
#pragma unmanaged
#include "UtilityClasses.h"
#pragma managed
#include "StdAfx.h"
#include "IconList.h"

using namespace System;
using namespace System::Runtime::InteropServices;
using namespace RP::Implementation::Owasp;

IconList::IconList(String^ path)
{
	IntPtr ptr = Marshal::StringToHGlobalUni(path);
	LPCWSTR str = (LPCWSTR)ptr.ToPointer();
	IconUtilityUnmanaged* unmanaged = new IconUtilityUnmanaged(str);
	// destroy pointer
	unmanaged->LoadIcons();
}
