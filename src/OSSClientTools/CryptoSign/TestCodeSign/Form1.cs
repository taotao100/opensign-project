using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using RP.Implementation.Owasp;
using RP.Implementation.Owasp.Crypto;
using System.Runtime.Remoting.Messaging;

namespace TestCodeSign
{
	/*
	 * 1. Allow the store location to be added through the file menu : DONE
	 * 2. Allow random generation of names for certificate : DONE
	 * 3. Add the EKUs and other things as per the form : DONE
	 * 4. Add the statusbar control feedback : DONE
	 * 5. Make the certificate generation process asynchronous : DONE
	 * 6. Add the capability to load parent signing certificate and allow to be installed to the windows store
	 * 7. Ensure that passworded private key is added to the file : DONE
	 */
	public partial class Form1 : Form
	{
		private CertificateStore<CodeSigningCertificate> certificateStore;
		private delegate void CompleteCertGeneration();
		private CompleteCertGeneration gen;

		public Form1()
		{
			InitializeComponent();
			InitGuiComponents();
			IconList list = new IconList(CertificateStore<CodeSigningCertificate>.CurrentInstance.certMgrPath);

			PopulateTreeView();
		}

		private void btnGenerate_Click(object sender, EventArgs e)
		{
			// put this here just in case the user hasn't selected the file path
			CookiesFile cookiesFile = CookiesFile.GetInstance();
			if (cookiesFile.StoreLocation != null)
			{
				certificateStore = CertificateStore<CodeSigningCertificate>.GetInstance(cookiesFile.StoreLocation);
			}
			    
			CodeSigningCertificateGenerator cert = new CodeSigningCertificateGenerator();
			cert.SignatureAlgorithm = (string)lstAlgorithms.SelectedItem;
			cert.StartDate = dtpStartDate.Value;
			cert.EndDate = dtpEndDate.Value;
			cert.IssuerDistinguishedName = txtIssuerDN.Text;
			cert.SubjectDistinguishedName = txtSubjectDN.Text;
			if (chkCodeSigning.Checked)
			{
				cert.AddCodeSigningOid();
			}
			if (chkBasicConstraints.Checked)
			{
				if (txtPathLen.Text.Length != 0 && (!Char.IsNumber(txtPathLen.Text.ToCharArray()[0])))
				{
					throw new ApplicationException("The PathLen field must be a number value");
				}

				cert.AddBasicContraintsOid(chkIsCA.Checked, int.Parse(txtPathLen.Text));
			}
			statusAlgorithm.Text = "Generating certificate at store location " + cookiesFile.StoreLocation;
			gen = new CompleteCertGeneration(cert.GenerateCertificate);

			gen.BeginInvoke(new AsyncCallback(CallbackStatusCertUpdate), cert);
		}

		private void CallbackStatusCertUpdate(IAsyncResult result)
		{
			if (result.IsCompleted)
			{
				// TODO: Check whether we can get away without switching to the window thread in case this blows
				CodeSigningCertificateGenerator generator = (CodeSigningCertificateGenerator)result.AsyncState;
				statusAlgorithm.Text = "Generated with filename " + generator.CertificateFilename;
			}
			CompleteCertGeneration gen = (CompleteCertGeneration)((AsyncResult)result).AsyncDelegate;
			gen.EndInvoke(result);
		}

		private void InitGuiComponents()
		{
			foreach (string alg in CodeSigningCertificateGenerator.Algorithms)
			{
				lstAlgorithms.Items.Add(alg);
			}

			dtpStartDate.Value = DateTime.Now;
			dtpEndDate.Value = DateTime.Now.AddYears(2);

			if (lstAlgorithms.Items.Count == 0)
			{
				throw new ApplicationException("unable to find available algorithms for generating certificate");
			}

			lstAlgorithms.SelectedIndex = 0;

			cmbKeyStrength.SelectedIndex = 1;
		}

		private void storeDirectoryToolStripMenuItem_Click(object sender, EventArgs e)
		{
			CookiesFile cookiesFile = CookiesFile.GetInstance();
			if (cookiesFile.StoreLocation != null)
			{
				fldBrowserStoreLocation.SelectedPath = cookiesFile.StoreLocation;
			}
			DialogResult result = fldBrowserStoreLocation.ShowDialog();
			if (result != DialogResult.Cancel)
			{
				certificateStore = CertificateStore<CodeSigningCertificate>.GetInstance(fldBrowserStoreLocation.SelectedPath);
			    // also persist the setting in a file
				cookiesFile.WriteKeyValue(CookiesFile.key_StoreLocation, fldBrowserStoreLocation.SelectedPath);
			}
		}

		private void chkBasicConstraints_CheckedChanged(object sender, EventArgs e)
		{
			if (chkBasicConstraints.Checked)
			{
				chkIsCA.Enabled = true;
				chkIsCA.ForeColor = SystemColors.ControlText;
				lblPathLength.ForeColor = SystemColors.ControlText;
				txtPathLen.Enabled = true;
			}
			else
			{
				chkIsCA.Enabled = false;
				chkIsCA.ForeColor = SystemColors.ControlDark;
				lblPathLength.ForeColor = SystemColors.ControlDark;
				txtPathLen.Enabled = false;
			}
		}

		private void chkAuthorityKeyIdentifier_CheckedChanged(object sender, EventArgs e)
		{
			if (chkAuthorityKeyIdentifier.Checked)
			{
				lblValue.ForeColor = SystemColors.ControlText;
				txtValue.Enabled = true;
			}
			else
			{
				lblValue.ForeColor = SystemColors.ControlDark;
				txtValue.Enabled = false;
			}
		}

		private void PopulateTreeView()
		{
			if (certificateStore == null)
			{
				try
				{
					CookiesFile cookiesFile = CookiesFile.GetInstance();
					if (cookiesFile.StoreLocation != null)
					{
						certificateStore = CertificateStore<CodeSigningCertificate>.GetInstance(cookiesFile.StoreLocation);
					}
				}
				catch (Exception)
				{
					certificateStore = CertificateStore<CodeSigningCertificate>.CurrentInstance;
				}
			}

			CodeSigningStoreBuilder builder = new CodeSigningStoreBuilder(certificateStore);
			builder.BuildStore();

			// Load the image list into the treeview if it isn't already present 
			if(tvCertificateList.ImageList == null)
			{
                //Icon icon = certificateStore.certificateIcon;
                //ImageList imageList = new ImageList();
                //imageList.Images.Add(icon);
                //tvCertificateList.ImageList = imageList;
			}

			foreach (CodeSigningCertificate cert in certificateStore)
			{
				TreeNode node = new TreeNode(cert.Filename, 0, 0);
				tvCertificateList.Nodes.Add(node);
			}
		}
	}
}
