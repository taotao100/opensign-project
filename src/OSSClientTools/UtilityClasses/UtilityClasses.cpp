// This is the main DLL file.


#include "stdafx.h"
#pragma unmanaged
#include "windows.h"
#include "UtilityClasses.h"

using namespace RP::Implementation::Owasp;

IconUtilityUnmanaged::IconUtilityUnmanaged(LPCWSTR path)
{
	hIconLibrary = LoadLibraryEx(path, 0, LOAD_LIBRARY_AS_IMAGE_RESOURCE);
}

IconUtilityUnmanaged::~IconUtilityUnmanaged()
{
	FreeLibrary(hIconLibrary);
}

void IconUtilityUnmanaged::LoadIcons(void)
{
	ENUMRESNAMEPROC lpFunction = &IconUtilityUnmanaged::EnumResNameProcImpl;
	BOOL retVal = EnumResourceNames(hIconLibrary, RT_GROUP_ICON, lpFunction, 0);
}

BOOL CALLBACK IconUtilityUnmanaged::EnumResNameProcImpl(HMODULE hModule, LPCWSTR lpszType, LPWSTR lpszName, LONG_PTR lParam)
{
	HRSRC hResourceGroup = FindResource(hModule, const_cast<LPCWSTR>(lpszName), lpszType);
	HGLOBAL hGlobalRes = LoadResource(hModule, hResourceGroup);
	unsigned int iResource = SizeofResource(hModule, hResourceGroup);
	ICONDIR* dir = (ICONDIR*)LockResource(hGlobalRes);
	for(unsigned int i = 0; i < dir->idCount; i++)
	{
		ICONDIRENTRY entry = dir->idEntries[i];
		// ad the check to get the data back here and marshal to byte array
	}
	return TRUE;
}
//unsafe public IconResource(Library library,
//                                   IntPtr pResourceName)
//        {
//            m_pName = pResourceName;
//
//            // Get the RT_GROUP_ICON resource data.
//
//            IntPtr hGroupInfo = library.FindResource(m_pName, 
//                                                     WindowsAPI.RT_GROUP_ICON);
//
//            IntPtr hGroupRes = library.LoadResource(hGroupInfo);
//            uint dwGroupResSize = library.SizeofResource(hGroupInfo);
//
//            WindowsAPI.MEMICONDIR* pDirectory = (WindowsAPI.MEMICONDIR*) Library.LockResource(hGroupRes);
//
//            // Get the RT_ICON resource data for each icon in the RT_GROUP_ICON.
//
//            m_images = new IconImage[pDirectory->wCount];
//
//            for (ushort i = 0; i < pDirectory->wCount; ++i)
//            {
//                WindowsAPI.MEMICONDIRENTRY* pEntry = GetDirectoryEntry(pDirectory, i);
//
//                m_images[i] = new IconImage(library, 
//                                            new IntPtr(pEntry->wId));
//            }
//        }