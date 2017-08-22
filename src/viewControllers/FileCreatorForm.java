package viewControllers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import helper.FileCreator;
import models.FileInfo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import swing2swt.layout.BorderLayout;

public class FileCreatorForm extends Dialog {

	protected Object result;
	protected Shell shell;
	protected Display display;
	private Text textName;
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
		setText("Create file");
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		
		shell.setMinimumSize(new Point(500, 250));
		shell.setSize(335, 121);
		shell.setImage(new Image(display, "c:/Users/marc.graciov/workspace/com.dorin.fileCreator/icons/fileIcon.ico"));
		shell.setText(getText());
		shell = getScreenCenteredShell();
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite compositeLeft = new Composite(shell, SWT.NONE);
		compositeLeft.setLayoutData(BorderLayout.WEST);
		
		Composite compositeCreateFile = new Composite(compositeLeft, SWT.NONE);
		compositeCreateFile.setBounds(0, 118, 243, 84);
		
		Button btnCreateFile = new Button(compositeCreateFile, SWT.NONE);
		btnCreateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createFile();
			}
		});
		btnCreateFile.setBounds(10, 24, 75, 25);
		btnCreateFile.setText("Create file");
		
		lblStatusMessage = new Label(compositeCreateFile, SWT.NONE);
		lblStatusMessage.setBounds(91, 24, 142, 22);
		
		Composite compositeLocation = new Composite(compositeLeft, SWT.NONE);
		compositeLocation.setBounds(0, 0, 243, 70);
		
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
		textLocation.setBounds(10, 32, 223, 38);
		
		Composite compositeName = new Composite(compositeLeft, SWT.NONE);
		compositeName.setBounds(0, 76, 243, 36);
		
		Label lblFileName = new Label(compositeName, SWT.NONE);
		lblFileName.setBounds(10, 10, 38, 15);
		lblFileName.setText("name");
		
		textName = new Text(compositeName, SWT.BORDER);
		textName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblStatusMessage.setText("");
			}
		});
		textName.setBounds(54, 7, 179, 21);
		
		Composite compositeRightContent = new Composite(shell, SWT.NONE);
		compositeRightContent.setLayout(new BorderLayout(0, 0));
		
		Label lblFileContent = new Label(compositeRightContent, SWT.NONE);
		lblFileContent.setLayoutData(BorderLayout.NORTH);
		lblFileContent.setText("content:");
		
		textContent = new Text(compositeRightContent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textContent.setLayoutData(BorderLayout.CENTER);
		textContent.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblStatusMessage.setText("");
			}
		});
		
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
	
	public Shell getScreenCenteredShell() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		Rectangle shellBounds = shell.getBounds();
		Rectangle screen = display.getMonitors()[0].getBounds();
		int x = (screen.width-shellBounds.width)/2;
		int y = (screen.height-shellBounds.height)/2;
		shell.setBounds(x, y, shellBounds.width, shellBounds.height);
		
		return shell;
	}
}
