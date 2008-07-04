namespace TestCodeSign
{
	partial class Form1
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.lblIssuerDN = new System.Windows.Forms.Label();
			this.lblSubjectDN = new System.Windows.Forms.Label();
			this.lblStartDate = new System.Windows.Forms.Label();
			this.lblEndDate = new System.Windows.Forms.Label();
			this.txtIssuerDN = new System.Windows.Forms.TextBox();
			this.txtSubjectDN = new System.Windows.Forms.TextBox();
			this.dtpStartDate = new System.Windows.Forms.DateTimePicker();
			this.dtpEndDate = new System.Windows.Forms.DateTimePicker();
			this.lblAlgorithms = new System.Windows.Forms.Label();
			this.lstAlgorithms = new System.Windows.Forms.ListBox();
			this.tbTestForm = new System.Windows.Forms.TabControl();
			this.tabPage1 = new System.Windows.Forms.TabPage();
			this.panel2 = new System.Windows.Forms.Panel();
			this.txtValue = new System.Windows.Forms.TextBox();
			this.lblValue = new System.Windows.Forms.Label();
			this.chkAuthorityKeyIdentifier = new System.Windows.Forms.CheckBox();
			this.panel1 = new System.Windows.Forms.Panel();
			this.txtPathLen = new System.Windows.Forms.TextBox();
			this.lblPathLength = new System.Windows.Forms.Label();
			this.chkIsCA = new System.Windows.Forms.CheckBox();
			this.chkBasicConstraints = new System.Windows.Forms.CheckBox();
			this.chkCodeSigning = new System.Windows.Forms.CheckBox();
			this.cmbKeyStrength = new System.Windows.Forms.ComboBox();
			this.lblKeyStrength = new System.Windows.Forms.Label();
			this.btnGenerate = new System.Windows.Forms.Button();
			this.tabPage2 = new System.Windows.Forms.TabPage();
			this.tvCertificateList = new System.Windows.Forms.TreeView();
			this.menuStrip1 = new System.Windows.Forms.MenuStrip();
			this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.storeDirectoryToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.statusStrip1 = new System.Windows.Forms.StatusStrip();
			this.statusAlgorithm = new System.Windows.Forms.ToolStripStatusLabel();
			this.fldBrowserStoreLocation = new System.Windows.Forms.FolderBrowserDialog();
			this.tbTestForm.SuspendLayout();
			this.tabPage1.SuspendLayout();
			this.panel2.SuspendLayout();
			this.panel1.SuspendLayout();
			this.tabPage2.SuspendLayout();
			this.menuStrip1.SuspendLayout();
			this.statusStrip1.SuspendLayout();
			this.SuspendLayout();
			// 
			// lblIssuerDN
			// 
			this.lblIssuerDN.AutoSize = true;
			this.lblIssuerDN.Location = new System.Drawing.Point(8, 17);
			this.lblIssuerDN.Name = "lblIssuerDN";
			this.lblIssuerDN.Size = new System.Drawing.Size(54, 13);
			this.lblIssuerDN.TabIndex = 0;
			this.lblIssuerDN.Text = "Issuer DN";
			// 
			// lblSubjectDN
			// 
			this.lblSubjectDN.AutoSize = true;
			this.lblSubjectDN.Location = new System.Drawing.Point(8, 67);
			this.lblSubjectDN.Name = "lblSubjectDN";
			this.lblSubjectDN.Size = new System.Drawing.Size(62, 13);
			this.lblSubjectDN.TabIndex = 1;
			this.lblSubjectDN.Text = "Subject DN";
			// 
			// lblStartDate
			// 
			this.lblStartDate.AutoSize = true;
			this.lblStartDate.Location = new System.Drawing.Point(10, 256);
			this.lblStartDate.Name = "lblStartDate";
			this.lblStartDate.Size = new System.Drawing.Size(55, 13);
			this.lblStartDate.TabIndex = 2;
			this.lblStartDate.Text = "Start Date\r\n";
			// 
			// lblEndDate
			// 
			this.lblEndDate.AutoSize = true;
			this.lblEndDate.Location = new System.Drawing.Point(8, 310);
			this.lblEndDate.Name = "lblEndDate";
			this.lblEndDate.Size = new System.Drawing.Size(64, 13);
			this.lblEndDate.TabIndex = 3;
			this.lblEndDate.Text = " Expiry Date";
			// 
			// txtIssuerDN
			// 
			this.txtIssuerDN.Location = new System.Drawing.Point(11, 33);
			this.txtIssuerDN.Name = "txtIssuerDN";
			this.txtIssuerDN.Size = new System.Drawing.Size(200, 20);
			this.txtIssuerDN.TabIndex = 4;
			this.txtIssuerDN.Text = "CN=Another Test CA";
			// 
			// txtSubjectDN
			// 
			this.txtSubjectDN.Location = new System.Drawing.Point(11, 83);
			this.txtSubjectDN.Name = "txtSubjectDN";
			this.txtSubjectDN.Size = new System.Drawing.Size(200, 20);
			this.txtSubjectDN.TabIndex = 5;
			this.txtSubjectDN.Text = "CN=Test CA";
			// 
			// dtpStartDate
			// 
			this.dtpStartDate.Location = new System.Drawing.Point(11, 272);
			this.dtpStartDate.Name = "dtpStartDate";
			this.dtpStartDate.Size = new System.Drawing.Size(200, 20);
			this.dtpStartDate.TabIndex = 6;
			// 
			// dtpEndDate
			// 
			this.dtpEndDate.Location = new System.Drawing.Point(11, 326);
			this.dtpEndDate.Name = "dtpEndDate";
			this.dtpEndDate.Size = new System.Drawing.Size(200, 20);
			this.dtpEndDate.TabIndex = 7;
			// 
			// lblAlgorithms
			// 
			this.lblAlgorithms.AutoSize = true;
			this.lblAlgorithms.Location = new System.Drawing.Point(10, 122);
			this.lblAlgorithms.Name = "lblAlgorithms";
			this.lblAlgorithms.Size = new System.Drawing.Size(55, 13);
			this.lblAlgorithms.TabIndex = 8;
			this.lblAlgorithms.Text = "Algorithms";
			// 
			// lstAlgorithms
			// 
			this.lstAlgorithms.FormattingEnabled = true;
			this.lstAlgorithms.Location = new System.Drawing.Point(11, 148);
			this.lstAlgorithms.Name = "lstAlgorithms";
			this.lstAlgorithms.Size = new System.Drawing.Size(200, 95);
			this.lstAlgorithms.TabIndex = 9;
			// 
			// tbTestForm
			// 
			this.tbTestForm.Controls.Add(this.tabPage1);
			this.tbTestForm.Controls.Add(this.tabPage2);
			this.tbTestForm.Location = new System.Drawing.Point(0, 27);
			this.tbTestForm.Name = "tbTestForm";
			this.tbTestForm.SelectedIndex = 0;
			this.tbTestForm.Size = new System.Drawing.Size(460, 394);
			this.tbTestForm.TabIndex = 10;
			// 
			// tabPage1
			// 
			this.tabPage1.Controls.Add(this.panel2);
			this.tabPage1.Controls.Add(this.panel1);
			this.tabPage1.Controls.Add(this.chkCodeSigning);
			this.tabPage1.Controls.Add(this.cmbKeyStrength);
			this.tabPage1.Controls.Add(this.lblKeyStrength);
			this.tabPage1.Controls.Add(this.btnGenerate);
			this.tabPage1.Controls.Add(this.lstAlgorithms);
			this.tabPage1.Controls.Add(this.lblIssuerDN);
			this.tabPage1.Controls.Add(this.lblAlgorithms);
			this.tabPage1.Controls.Add(this.lblSubjectDN);
			this.tabPage1.Controls.Add(this.dtpEndDate);
			this.tabPage1.Controls.Add(this.lblStartDate);
			this.tabPage1.Controls.Add(this.dtpStartDate);
			this.tabPage1.Controls.Add(this.lblEndDate);
			this.tabPage1.Controls.Add(this.txtSubjectDN);
			this.tabPage1.Controls.Add(this.txtIssuerDN);
			this.tabPage1.Location = new System.Drawing.Point(4, 22);
			this.tabPage1.Name = "tabPage1";
			this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
			this.tabPage1.Size = new System.Drawing.Size(452, 368);
			this.tabPage1.TabIndex = 0;
			this.tabPage1.Text = "X509 Certificates";
			this.tabPage1.UseVisualStyleBackColor = true;
			// 
			// panel2
			// 
			this.panel2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.panel2.Controls.Add(this.txtValue);
			this.panel2.Controls.Add(this.lblValue);
			this.panel2.Controls.Add(this.chkAuthorityKeyIdentifier);
			this.panel2.Location = new System.Drawing.Point(231, 214);
			this.panel2.Name = "panel2";
			this.panel2.Size = new System.Drawing.Size(198, 78);
			this.panel2.TabIndex = 17;
			// 
			// txtValue
			// 
			this.txtValue.Enabled = false;
			this.txtValue.Location = new System.Drawing.Point(82, 42);
			this.txtValue.Name = "txtValue";
			this.txtValue.Size = new System.Drawing.Size(104, 20);
			this.txtValue.TabIndex = 17;
			// 
			// lblValue
			// 
			this.lblValue.AutoSize = true;
			this.lblValue.ForeColor = System.Drawing.SystemColors.ControlDark;
			this.lblValue.Location = new System.Drawing.Point(11, 45);
			this.lblValue.Name = "lblValue";
			this.lblValue.Size = new System.Drawing.Size(34, 13);
			this.lblValue.TabIndex = 16;
			this.lblValue.Text = "Value";
			// 
			// chkAuthorityKeyIdentifier
			// 
			this.chkAuthorityKeyIdentifier.AutoSize = true;
			this.chkAuthorityKeyIdentifier.Location = new System.Drawing.Point(13, 16);
			this.chkAuthorityKeyIdentifier.Name = "chkAuthorityKeyIdentifier";
			this.chkAuthorityKeyIdentifier.Size = new System.Drawing.Size(131, 17);
			this.chkAuthorityKeyIdentifier.TabIndex = 14;
			this.chkAuthorityKeyIdentifier.Text = "Authority Key Identifier";
			this.chkAuthorityKeyIdentifier.UseVisualStyleBackColor = true;
			this.chkAuthorityKeyIdentifier.CheckedChanged += new System.EventHandler(this.chkAuthorityKeyIdentifier_CheckedChanged);
			// 
			// panel1
			// 
			this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.panel1.Controls.Add(this.txtPathLen);
			this.panel1.Controls.Add(this.lblPathLength);
			this.panel1.Controls.Add(this.chkIsCA);
			this.panel1.Controls.Add(this.chkBasicConstraints);
			this.panel1.Location = new System.Drawing.Point(229, 108);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(200, 100);
			this.panel1.TabIndex = 16;
			// 
			// txtPathLen
			// 
			this.txtPathLen.Enabled = false;
			this.txtPathLen.Location = new System.Drawing.Point(81, 66);
			this.txtPathLen.MaxLength = 1;
			this.txtPathLen.Name = "txtPathLen";
			this.txtPathLen.Size = new System.Drawing.Size(104, 20);
			this.txtPathLen.TabIndex = 17;
			// 
			// lblPathLength
			// 
			this.lblPathLength.AutoSize = true;
			this.lblPathLength.ForeColor = System.Drawing.SystemColors.ControlDark;
			this.lblPathLength.Location = new System.Drawing.Point(10, 69);
			this.lblPathLength.Name = "lblPathLength";
			this.lblPathLength.Size = new System.Drawing.Size(65, 13);
			this.lblPathLength.TabIndex = 16;
			this.lblPathLength.Text = "Path Length";
			// 
			// chkIsCA
			// 
			this.chkIsCA.AutoSize = true;
			this.chkIsCA.Enabled = false;
			this.chkIsCA.ForeColor = System.Drawing.SystemColors.ControlDark;
			this.chkIsCA.Location = new System.Drawing.Point(14, 39);
			this.chkIsCA.Name = "chkIsCA";
			this.chkIsCA.Size = new System.Drawing.Size(51, 17);
			this.chkIsCA.TabIndex = 15;
			this.chkIsCA.Text = "Is CA";
			this.chkIsCA.UseVisualStyleBackColor = true;
			// 
			// chkBasicConstraints
			// 
			this.chkBasicConstraints.AutoSize = true;
			this.chkBasicConstraints.Location = new System.Drawing.Point(14, 16);
			this.chkBasicConstraints.Name = "chkBasicConstraints";
			this.chkBasicConstraints.Size = new System.Drawing.Size(104, 17);
			this.chkBasicConstraints.TabIndex = 14;
			this.chkBasicConstraints.Text = "BasicConstraints";
			this.chkBasicConstraints.UseVisualStyleBackColor = true;
			this.chkBasicConstraints.CheckedChanged += new System.EventHandler(this.chkBasicConstraints_CheckedChanged);
			// 
			// chkCodeSigning
			// 
			this.chkCodeSigning.AutoSize = true;
			this.chkCodeSigning.Checked = true;
			this.chkCodeSigning.CheckState = System.Windows.Forms.CheckState.Checked;
			this.chkCodeSigning.Location = new System.Drawing.Point(242, 85);
			this.chkCodeSigning.Name = "chkCodeSigning";
			this.chkCodeSigning.Size = new System.Drawing.Size(89, 17);
			this.chkCodeSigning.TabIndex = 13;
			this.chkCodeSigning.Text = "Code Signing";
			this.chkCodeSigning.UseVisualStyleBackColor = true;
			// 
			// cmbKeyStrength
			// 
			this.cmbKeyStrength.FormattingEnabled = true;
			this.cmbKeyStrength.Items.AddRange(new object[] {
            "1024",
            "2048",
            "3072",
            "4096"});
			this.cmbKeyStrength.Location = new System.Drawing.Point(242, 33);
			this.cmbKeyStrength.Name = "cmbKeyStrength";
			this.cmbKeyStrength.Size = new System.Drawing.Size(121, 21);
			this.cmbKeyStrength.TabIndex = 12;
			// 
			// lblKeyStrength
			// 
			this.lblKeyStrength.AutoSize = true;
			this.lblKeyStrength.Location = new System.Drawing.Point(239, 17);
			this.lblKeyStrength.Name = "lblKeyStrength";
			this.lblKeyStrength.Size = new System.Drawing.Size(65, 13);
			this.lblKeyStrength.TabIndex = 11;
			this.lblKeyStrength.Text = "KeyStrength";
			// 
			// btnGenerate
			// 
			this.btnGenerate.Location = new System.Drawing.Point(264, 323);
			this.btnGenerate.Name = "btnGenerate";
			this.btnGenerate.Size = new System.Drawing.Size(137, 23);
			this.btnGenerate.TabIndex = 10;
			this.btnGenerate.Text = "Generate >>";
			this.btnGenerate.UseVisualStyleBackColor = true;
			this.btnGenerate.Click += new System.EventHandler(this.btnGenerate_Click);
			// 
			// tabPage2
			// 
			this.tabPage2.Controls.Add(this.tvCertificateList);
			this.tabPage2.Location = new System.Drawing.Point(4, 22);
			this.tabPage2.Name = "tabPage2";
			this.tabPage2.Size = new System.Drawing.Size(452, 368);
			this.tabPage2.TabIndex = 1;
			this.tabPage2.Text = "Certificate Store";
			this.tabPage2.UseVisualStyleBackColor = true;
			// 
			// tvCertificateList
			// 
			this.tvCertificateList.Location = new System.Drawing.Point(3, 3);
			this.tvCertificateList.Name = "tvCertificateList";
			this.tvCertificateList.Size = new System.Drawing.Size(323, 362);
			this.tvCertificateList.TabIndex = 0;
			// 
			// menuStrip1
			// 
			this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem});
			this.menuStrip1.Location = new System.Drawing.Point(0, 0);
			this.menuStrip1.Name = "menuStrip1";
			this.menuStrip1.Size = new System.Drawing.Size(460, 24);
			this.menuStrip1.TabIndex = 11;
			this.menuStrip1.Text = "menuStrip1";
			// 
			// fileToolStripMenuItem
			// 
			this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.storeDirectoryToolStripMenuItem,
            this.exitToolStripMenuItem});
			this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
			this.fileToolStripMenuItem.Size = new System.Drawing.Size(35, 20);
			this.fileToolStripMenuItem.Text = "File";
			// 
			// storeDirectoryToolStripMenuItem
			// 
			this.storeDirectoryToolStripMenuItem.Name = "storeDirectoryToolStripMenuItem";
			this.storeDirectoryToolStripMenuItem.Size = new System.Drawing.Size(173, 22);
			this.storeDirectoryToolStripMenuItem.Text = "Store Directory ...";
			this.storeDirectoryToolStripMenuItem.Click += new System.EventHandler(this.storeDirectoryToolStripMenuItem_Click);
			// 
			// exitToolStripMenuItem
			// 
			this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
			this.exitToolStripMenuItem.Size = new System.Drawing.Size(173, 22);
			this.exitToolStripMenuItem.Text = "E&xit";
			// 
			// statusStrip1
			// 
			this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.statusAlgorithm});
			this.statusStrip1.Location = new System.Drawing.Point(0, 420);
			this.statusStrip1.Name = "statusStrip1";
			this.statusStrip1.Size = new System.Drawing.Size(460, 22);
			this.statusStrip1.TabIndex = 12;
			this.statusStrip1.Text = "statusStrip1";
			// 
			// statusAlgorithm
			// 
			this.statusAlgorithm.Name = "statusAlgorithm";
			this.statusAlgorithm.Size = new System.Drawing.Size(171, 17);
			this.statusAlgorithm.Text = "Waiting for Certificate Request ...";
			// 
			// fldBrowserStoreLocation
			// 
			this.fldBrowserStoreLocation.SelectedPath = "C:\\";
			// 
			// Form1
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(460, 442);
			this.Controls.Add(this.statusStrip1);
			this.Controls.Add(this.tbTestForm);
			this.Controls.Add(this.menuStrip1);
			this.Name = "Form1";
			this.Text = "CA Certificate Generator";
			this.tbTestForm.ResumeLayout(false);
			this.tabPage1.ResumeLayout(false);
			this.tabPage1.PerformLayout();
			this.panel2.ResumeLayout(false);
			this.panel2.PerformLayout();
			this.panel1.ResumeLayout(false);
			this.panel1.PerformLayout();
			this.tabPage2.ResumeLayout(false);
			this.menuStrip1.ResumeLayout(false);
			this.menuStrip1.PerformLayout();
			this.statusStrip1.ResumeLayout(false);
			this.statusStrip1.PerformLayout();
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.Label lblIssuerDN;
		private System.Windows.Forms.Label lblSubjectDN;
		private System.Windows.Forms.Label lblStartDate;
		private System.Windows.Forms.Label lblEndDate;
		private System.Windows.Forms.TextBox txtIssuerDN;
		private System.Windows.Forms.TextBox txtSubjectDN;
		private System.Windows.Forms.DateTimePicker dtpStartDate;
		private System.Windows.Forms.DateTimePicker dtpEndDate;
		private System.Windows.Forms.Label lblAlgorithms;
		private System.Windows.Forms.ListBox lstAlgorithms;
		private System.Windows.Forms.TabControl tbTestForm;
		private System.Windows.Forms.TabPage tabPage1;
		private System.Windows.Forms.ComboBox cmbKeyStrength;
		private System.Windows.Forms.Label lblKeyStrength;
		private System.Windows.Forms.Button btnGenerate;
		private System.Windows.Forms.CheckBox chkBasicConstraints;
		private System.Windows.Forms.CheckBox chkCodeSigning;
		private System.Windows.Forms.MenuStrip menuStrip1;
		private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem storeDirectoryToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
		private System.Windows.Forms.Panel panel2;
		private System.Windows.Forms.TextBox txtValue;
		private System.Windows.Forms.Label lblValue;
		private System.Windows.Forms.CheckBox chkAuthorityKeyIdentifier;
		private System.Windows.Forms.Panel panel1;
		private System.Windows.Forms.TextBox txtPathLen;
		private System.Windows.Forms.Label lblPathLength;
		private System.Windows.Forms.CheckBox chkIsCA;
		private System.Windows.Forms.StatusStrip statusStrip1;
		private System.Windows.Forms.ToolStripStatusLabel statusAlgorithm;
		private System.Windows.Forms.FolderBrowserDialog fldBrowserStoreLocation;
		private System.Windows.Forms.TabPage tabPage2;
		private System.Windows.Forms.TreeView tvCertificateList;
	}
}

