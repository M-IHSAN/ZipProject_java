package com.oneact.zipfilemaker.window;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.*;


public class Zipper
{
	private ArrayList<String> m_fileList = new ArrayList<String>();
	
	public Zipper()
	{

	}
	
	// Add a file to be zipped
	public int addFile(String filePath)
	{
		int retVal = 0;
		
		// Check if the file already exist in the list
		for(int i = 0; i < m_fileList.size(); i++)
		{
			if(filePath.equals(m_fileList.get(i)))
			{
				retVal = -1;
				break;
			}
		}
		
		if(retVal == 0)
			m_fileList.add(filePath);
		
		return retVal;
	}
	
	// Get the list size
	public int getListSize()
	{
		return m_fileList.size();
	}
	
	// Remove a file from the list
	public void removeAt(int index)
	{
		m_fileList.remove(index);
	}
	
	// Remove all the files from the list
	public void removeAll()
	{
		m_fileList.removeAll(m_fileList);
	}
	
	// Get a string containing the list of added files (for displaying)
	public String getFilesString()
	{
		String str = "";
		
		for(int i = 0; i<m_fileList.size(); i++)
		{
			str += m_fileList.get(i);
			str += "\n";
		}
		
		return str;
	}
	
	// Zip all the files in the archive
	public void zipFiles(String zipFilePath) throws IOException
	{
		final int BUFFER = 2048;
		byte data[] = new byte[BUFFER];
		
		// Output file stream creation
		FileOutputStream dest= new FileOutputStream(zipFilePath);
		
		// Output buffer creation
		BufferedOutputStream buff = new BufferedOutputStream(dest);
		
		// Writing stream creation
		ZipOutputStream out = new ZipOutputStream(buff);
		
		// Set the compression method
		out.setMethod(ZipOutputStream.DEFLATED);
		
		// Sate the compression level (0 - 9)
		out.setLevel(9);
		
		for(int i = 0; i<m_fileList.size(); i++)
		{
		    FileInputStream fi = new FileInputStream(m_fileList.get(i));
		    BufferedInputStream buffi = new BufferedInputStream(fi, BUFFER);
		    
		    File file = new File(m_fileList.get(i));
		    ZipEntry entry= new ZipEntry(file.getName());
		    out.putNextEntry(entry);
		    
		    // Write the entry in the output stream
		    int count;
		    while((count = buffi.read(data, 0, BUFFER)) != -1)
		    {
		        out.write(data, 0, count);
		    }
		    
		    // Close the entry
		    out.closeEntry();
		    
		    // Close the stream
		    buffi.close();
		}
		out.close();
		
	}
	
	// Extract files from the archive
	public void unzip(String zipFilePath) throws IOException
	{
		final int BUFFER = 2048;
		byte data[] = new byte[BUFFER];
		String folderStr = "";
		
		// Destination file
		BufferedOutputStream dest = null;
		
		// Open the file to unzip
		FileInputStream fis = new FileInputStream(zipFilePath);
		
		// Open a buffer for this file
		BufferedInputStream buffi = new BufferedInputStream(fis);
		
		// Open the file through the buffer
		ZipInputStream zis = new ZipInputStream(buffi);
		
		ZipEntry entry;
		
		//Get the archive path
		folderStr = removeExtension(zipFilePath);
		
		// Create a new folder
		File dir = new File (folderStr);
		dir.mkdirs();
		
		while((entry = zis.getNextEntry()) != null)
		{
			// Output file creation (with the entry file name)
			FileOutputStream fos = new FileOutputStream(folderStr + "/" + entry.getName());
			dest = new BufferedOutputStream(fos, BUFFER);
			
			// Write on disk
			int count = 0;
			while ((count = zis.read(data, 0, BUFFER)) != -1)
		        dest.write(data, 0, count);
		    
			dest.flush();
			dest.close();
		}
		
		zis.close();
		
	}
	
	// Remove file path extension method
	public String removeExtension(String str)
	{
        // Handle null case specially.
        if (str == null)
        	return null;

        // Get position of last '.'.
        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if (pos == -1)
        	return str;

        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }
	
	// Check if file exist
	public boolean fileExist(String str)
	{
		boolean retVal = false;
		
		File f = new File(str);
		if(f.exists() && !f.isDirectory()) 
		    retVal = true;
		
        return retVal;
    }
	
	// Delete file
	public void deleteFile(String str)
	{
		try
		{
    		File file = new File(str);
    		file.delete();
    	}
		catch(Exception e)
		{
    		e.printStackTrace();
    	}
    }
}
