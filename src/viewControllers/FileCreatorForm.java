package viewControllers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import helper.FileCreator;
import models.FileInfo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class FileCreatorForm extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text textName;
	private Text textExtension;
	private Text textLocation;
	private Text textContent;
	private DirectoryDialog dialogFileLocator;
	private Label lblStatusMessage;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FileCreatorForm(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	public FileCreatorForm(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setMinimumSize(new Point(500, 250));
		shell.setSize(500, 52);
		shell.setText(getText());
		
		Composite compositeName = new Composite(shell, SWT.NONE);
		compositeName.setBounds(10, 10, 243, 36);
		
		Label lblFileName = new Label(compositeName, SWT.NONE);
		lblFileName.setBounds(10, 10, 38, 15);
		lblFileName.setText("name");
		
		textName = new Text(compositeName, SWT.BORDER);
		textName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblStatusMessage.setText("");
			}
		});
		textName.setBounds(75, 7, 131, 21);
		
		Composite compositeExtension = new Composite(shell, SWT.NONE);
		compositeExtension.setBounds(10, 52, 243, 36);
		
		Label lblFileExtension = new Label(compositeExtension, SWT.NONE);
		lblFileExtension.setText("extension");
		lblFileExtension.setBounds(10, 10, 50, 15);
		
		textExtension = new Text(compositeExtension, SWT.BORDER);
		textExtension.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblStatusMessage.setText("");
			}
		});
		textExtension.setBounds(75, 7, 130, 21);
		
		Composite compositeLocation = new Composite(shell, SWT.NONE);
		compositeLocation.setBounds(10, 100, 243, 70);
		
		Label lblFileLocation = new Label(compositeLocation, SWT.NONE);
		lblFileLocation.setText("file location:");
		lblFileLocation.setBounds(10, 11, 91, 15);
		
		Button btnNewButton = new Button(compositeLocation, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getLocationSelector();
			}
		});
		btnNewButton.setBounds(212, 32, 21, 38);
		btnNewButton.setText("...");
		
		textLocation = new Text(compositeLocation, SWT.BORDER | SWT.H_SCROLL | SWT.CANCEL);
		textLocation.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblStatusMessage.setText("");
			}
		});
		textLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				getLocationSelector();
			}
		});
		textLocation.setBounds(10, 32, 223, 38);
		
		Composite compositeContent = new Composite(shell, SWT.NONE);
		compositeContent.setBounds(259, 10, 224, 150);
		
		textContent = new Text(compositeContent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textContent.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblStatusMessage.setText("");
			}
		});
		textContent.setBounds(10, 40, 204, 100);
		
		Label lblFileContent = new Label(compositeContent, SWT.NONE);
		lblFileContent.setBounds(10, 10, 86, 15);
		lblFileContent.setText("content:");
		
		Composite compositeCreateFile = new Composite(shell, SWT.NONE);
		compositeCreateFile.setBounds(10, 176, 243, 46);
		
		Button btnCreateFile = new Button(compositeCreateFile, SWT.NONE);
		btnCreateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createFile();
			}
		});
		btnCreateFile.setBounds(74, 10, 75, 25);
		btnCreateFile.setText("Create file");
		
		Composite compositeStatus = new Composite(shell, SWT.NONE);
		compositeStatus.setBounds(269, 166, 214, 56);
		
		Label lblStatus = new Label(compositeStatus, SWT.NONE);
		lblStatus.setBounds(10, 10, 55, 15);
		lblStatus.setText("Status:");
		
		lblStatusMessage = new Label(compositeStatus, SWT.NONE);
		lblStatusMessage.setBounds(10, 31, 194, 15);
		
	}
	
	private void getLocationSelector() {
		if (dialogFileLocator == null) {
			dialogFileLocator = new DirectoryDialog(shell);
			dialogFileLocator.setFilterPath("c:\\");
			dialogFileLocator.setText("Choose file location");
		} 
		
		String result = dialogFileLocator.open();
		
		if (result != null) {
			dialogFileLocator.setFilterPath(result);
			textLocation.setText(result);
		}
	}
	
	private void createFile() {
		FileInfo fileInfo = new FileInfo(
				textName.getText(), 
				textExtension.getText(),
				textContent.getText(),
				textLocation.getText()
		);
		
		try {
			new FileCreator().createFile(fileInfo);
			lblStatusMessage.setText("File created successfully!");
		} catch (Exception ex) {
			lblStatusMessage.setText("Failed to create the file!");
			shell.close();
			String errorMessage = "Error class: " + ex.getClass() + "\n\n Error message: " + ex.getMessage();
			MessageDialog.openError(shell, "Failed to create file", errorMessage);
		}
	}
}
