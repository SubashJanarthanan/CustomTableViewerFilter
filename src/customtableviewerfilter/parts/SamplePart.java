package customtableviewerfilter.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * The Class SamplePart.
 * 
 * @author subash
 * 
 */
public class SamplePart {

	/** The txt input. */
	private Text txtInput;

	/** The table viewer. */
	private TableViewer tableViewer;

	/**
	 * Creates the composite.
	 *
	 * @param parent
	 *            the parent
	 */
	@PostConstruct
	public void createComposite(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("Enter text to search...");
		txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		List<String> initialDataModel = createInitialDataModel();

		tableViewer = new TableViewer(parent);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(initialDataModel);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		txtInput.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				final String searchTxt = ((Text) e.widget).getText().trim();
				filterTableViewer(searchTxt, initialDataModel);
			}
		});
	}

	/**
	 * Filter table viewer.
	 *
	 * @param searchTxt
	 *            the search txt
	 * @param createInitialDataModel
	 *            the create initial data model
	 */
	private void filterTableViewer(final String searchTxt, final List<String> createInitialDataModel) {
		this.tableViewer.setSelection(null);
		List<String> childObjTempList = createInitialDataModel;
		childObjTempList = createInitialDataModel.stream()
				.filter(s -> ((String) s).toLowerCase().contains(searchTxt.toLowerCase())).collect(Collectors.toList());
		this.tableViewer.setInput(childObjTempList);
	}

	/**
	 * Sets the focus.
	 */
	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	/**
	 * Creates the initial data model.
	 *
	 * @return the list
	 */
	private List<String> createInitialDataModel() {
		List<String> listOfData = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			listOfData.add("TableItemCustomData " + i);
		}

		return listOfData;
	}
}