// UtilityClasses.h

#pragma once
#pragma unmanaged
#include "windows.h"
namespace RP {
	namespace Implementation { 
		namespace Owasp {

			class IconUtilityUnmanaged
			{
			public:
				IconUtilityUnmanaged(LPCWSTR path);
				~IconUtilityUnmanaged();
				void LoadIcons(void);
				// callback function
				static BOOL CALLBACK EnumResNameProcImpl(HMODULE hModule, LPCWSTR lpszType, LPWSTR lpszName, LONG_PTR lParam);
			private:
				LPCWSTR m_path;
				// unmanaged
				HMODULE hIconLibrary;
			};

			typedef struct
			{
				BYTE        bWidth;          // Width, in pixels, of the image
				BYTE        bHeight;         // Height, in pixels, of the image
				BYTE        bColorCount;     // Number of colors in image (0 if >=8bpp)
				BYTE        bReserved;       // Reserved ( must be 0)
				WORD        wPlanes;         // Color Planes
				WORD        wBitCount;       // Bits per pixel
				DWORD       dwBytesInRes;    // How many bytes in this resource?
				DWORD       dwImageOffset;   // Where in the file is this image?
			} ICONDIRENTRY, *LPICONDIRENTRY;

			typedef struct
			{
				WORD           idReserved;   // Reserved (must be 0)
				WORD           idType;       // Resource Type (1 for icons)
				WORD           idCount;      // How many images?
				ICONDIRENTRY   idEntries[1]; // An entry for each image (idCount of 'em)
			} ICONDIR, *LPICONDIR;

			

		}
	}
}
