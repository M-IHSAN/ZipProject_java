package com.oneact.zipfilemaker.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

public class MainWindow extends JFrame
{
	private JPanel  m_container = new JPanel();
	private JPanel  m_horizContainer0 = new JPanel();
	private JPanel  m_horizContainer1 = new JPanel();
	private JPanel  m_horizContainer2 = new JPanel();
	private JPanel  m_horizContainer3 = new JPanel();
	private JPanel  m_horizContainer4 = new JPanel();
	private JPanel  m_horizContainer5 = new JPanel();
	private JPanel  m_horizContainer6 = new JPanel();
	private JPanel  m_horizContainer7 = new JPanel();
	
	// Labels
	private JLabel m_fileBrowseLabel = new JLabel("Choose the files to add in the Zip folder");
	private JLabel m_zipTitleLabel = new JLabel("Zip");
	private JLabel m_unZipTitleLabel = new JLabel("UnZip");
	private JLabel m_zippedFileBrowseLabel = new JLabel("Choose a Zip file you want to UnZip");

	// Files List
	private DefaultListModel m_model = new DefaultListModel();
    private JList m_list = new JList(m_model);
    private JScrollPane m_scrollPane = new JScrollPane(m_list);
    
    // Buttons
    private JButton m_removeButton = new JButton("Remove");
    private JButton m_removeAllButton = new JButton("Remove All");
	
    // Zipper class
	private Zipper m_zipper = new Zipper();
	
	public MainWindow()
	{
		// Set the frame properties
		setTitle("Zip File Maker");
		setSize(400, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// Add the buttons
		m_container.setLayout(new BoxLayout(m_container, BoxLayout.PAGE_AXIS));
		m_horizContainer0.setLayout(new BoxLayout(m_horizContainer0, BoxLayout.LINE_AXIS));
		m_horizContainer1.setLayout(new BoxLayout(m_horizContainer1, BoxLayout.LINE_AXIS));
		m_horizContainer2.setLayout(new BoxLayout(m_horizContainer2, BoxLayout.LINE_AXIS));
		m_horizContainer3.setLayout(new BoxLayout(m_horizContainer3, BoxLayout.LINE_AXIS));
		m_horizContainer4.setLayout(new BoxLayout(m_horizContainer4, BoxLayout.LINE_AXIS));
		m_horizContainer5.setLayout(new BoxLayout(m_horizContainer5, BoxLayout.LINE_AXIS));
		m_horizContainer6.setLayout(new BoxLayout(m_horizContainer6, BoxLayout.LINE_AXIS));
		m_horizContainer7.setLayout(new BoxLayout(m_horizContainer7, BoxLayout.LINE_AXIS));


		m_horizContainer0.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer1.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer2.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer3.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer4.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer5.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer6.setAlignmentX(Component.LEFT_ALIGNMENT );
		m_horizContainer7.setAlignmentX(Component.LEFT_ALIGNMENT );
		
		// Zip Title Label
		m_zipTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		m_zipTitleLabel.setFont(new Font("Trebuchet", Font.BOLD, 20));
		m_zipTitleLabel.setForeground(Color.DARK_GRAY);
		m_horizContainer0.add(m_zipTitleLabel, BorderLayout.CENTER);
		
		// Zip Label
		m_fileBrowseLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		m_horizContainer1.add(m_fileBrowseLabel);
		
		// Zip browse button
		addFileBrowser();
		
		// List
		m_scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Remove buttons
		addRemoveButton();
		addRemoveAllButton();

		// Zip Button
		addDestFileBrowser();
		
		// UnZip Title Label
		m_unZipTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		m_unZipTitleLabel.setFont(new Font("Trebuchet", Font.BOLD, 20));
		m_unZipTitleLabel.setForeground(Color.darkGray);
		m_horizContainer5.add(m_unZipTitleLabel, BorderLayout.CENTER);
		
		// Unzip label
		m_zippedFileBrowseLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		m_horizContainer6.add(m_zippedFileBrowseLabel);
		
		// Unzip button
		addUnzipButton();
		
		// Organize the panes
		m_container.add(m_horizContainer0, BorderLayout.WEST);
		m_container.add(m_horizContainer1, BorderLayout.WEST);
		m_container.add(m_horizContainer2, BorderLayout.WEST);
		m_container.add(m_scrollPane);
		m_container.add(m_horizContainer3, BorderLayout.WEST);
		m_container.add(m_horizContainer4, BorderLayout.WEST);
		m_container.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);
		m_container.add(m_horizContainer5, BorderLayout.WEST);
		m_container.add(m_horizContainer6, BorderLayout.WEST);
		m_container.add(m_horizContainer7, BorderLayout.WEST);
		
		setContentPane(m_container);
	
	}
	
	// Add file browser method
	private void addFileBrowser()
	{
		final JFileChooser fileDialog = new JFileChooser();
		JButton showFileDialogButton = new JButton("Add File");
	    showFileDialogButton.addActionListener(new ActionListener()
	    {
	    	@Override
	    	public void actionPerformed(ActionEvent e)
	    	{
				fileDialog.setApproveButtonText("Select");
	            int returnVal = fileDialog.showOpenDialog(getParent());
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	java.io.File file = fileDialog.getSelectedFile();
	            	if(m_zipper.addFile(file.getPath()) == 0)
	            		m_model.addElement(file.getName());
	            	else
	            		// Display an error popup
	            		JOptionPane.showMessageDialog(getParent(), "File already selected!", "Zip File Maker", JOptionPane.WARNING_MESSAGE);
	            } 
	         }
	    });
	    m_horizContainer2.add(showFileDialogButton);
	}
	
	// Destination file browser method
	private void addDestFileBrowser()
	{
		final JFileChooser fileDialog = new JFileChooser();
		JButton showFileDialogButton = new JButton("Zip Files");
	    showFileDialogButton.addActionListener(new ActionListener()
	    {
	    	@Override
	    	public void actionPerformed(ActionEvent e)
	    	{
    			if(m_model.getSize() > 0)
    			{
    				fileDialog.setSelectedFile(new File("archive.zip"));
    				fileDialog.setApproveButtonText("Zip");
		            int returnVal = fileDialog.showDialog(getParent(), "Zip");
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		               java.io.File file = fileDialog.getSelectedFile();
		               
		               // Check if the file already exist
		               if(m_zipper.fileExist(file.getPath()))
		               {
		            	   // Display overwrite popup
		            	   if(JOptionPane.showConfirmDialog(getParent(), "File already exist!\nOverwrite it?", "Zip File Maker", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		            	       m_zipper.deleteFile(file.getPath());
		            	   else
		            		   return;
		               }
		               
		               try {
		            	   m_zipper.zipFiles(file.getPath());
		            	   
		            		// Display an error popup
		            		JOptionPane.showMessageDialog(getParent(), "Files Zipped!", "Zip File Maker", JOptionPane.INFORMATION_MESSAGE);
		               } 
		               catch (IOException e1) {
		            		// Display an error popup
		            	   JOptionPane.showMessageDialog(getParent(), "Unable to zip the files!", "Zip File Maker", JOptionPane.WARNING_MESSAGE);
		            	   e1.printStackTrace();
		               }
		            }
    			}
			    else
			    {
			    // Display an error popup
			    JOptionPane.showMessageDialog(getParent(), "No File Selected!", "Zip File Maker", JOptionPane.WARNING_MESSAGE);
			    }   
	    	}
	    });
	    m_horizContainer4.add(showFileDialogButton, BorderLayout.LINE_START);
	}
	

	// Add remove Button method
	private void addRemoveButton()
	{
		m_removeButton.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					int index = m_list.getSelectedIndex();
					if (m_model.getSize() > 0)
						m_model.removeElementAt(index);
					if (m_zipper.getListSize() > 0)
						m_zipper.removeAt(index);
			      }
			}
		);
		m_horizContainer3.add(m_removeButton);
	}

	// Add remove all Button method
	private void addRemoveAllButton()
	{
		m_removeAllButton.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if (m_model.getSize() > 0)
						m_model.removeAllElements();
					if (m_zipper.getListSize() > 0)
						m_zipper.removeAll();
			      }
			}
		);
		m_horizContainer3.add(m_removeAllButton);
	}
	
	// Add file browser method
	private void addUnzipButton()
	{
		final JFileChooser fileDialog = new JFileChooser();
		JButton showFileDialogButton = new JButton("Browse File to Unzip");
	    showFileDialogButton.addActionListener(new ActionListener()
	    {
	    	@Override
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		// Rename "open" button
				fileDialog.setApproveButtonText("UnZip");
				
				// Filter only .zip files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Zip Files", "zip");
				fileDialog.setFileFilter(filter);
				
	            int returnVal = fileDialog.showOpenDialog(getParent());
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	java.io.File file = fileDialog.getSelectedFile();
	            	try {
						m_zipper.unzip(file.getPath());
						
	            		// Display an error popup
	            		JOptionPane.showMessageDialog(getParent(), "Archive Unzipped!", "Zip File Maker", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
	            		// Display an error popup
	            		JOptionPane.showMessageDialog(getParent(), "Unable to unzip the file!", "Zip File Maker", JOptionPane.WARNING_MESSAGE);
						e1.printStackTrace();
					}

	            }     
	         }
	    });
	    m_horizContainer7.add(showFileDialogButton);
	}
}


