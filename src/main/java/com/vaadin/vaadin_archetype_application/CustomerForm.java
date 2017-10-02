package com.vaadin.vaadin_archetype_application;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerForm extends FormLayout {

	private TextField firstname =  new TextField("First name");
	private TextField lastname =  new TextField("Last name");
	private TextField email =  new TextField("Email");
	private NativeSelect<CustomerStatus> status =  new NativeSelect("Status");
	private DateField birthday = new DateField("Brirthday");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private CustomerService service = CustomerService.getInstance();
	private Customer customer;
	private MyUI myUI;
	
	private Binder<Customer> binder = new Binder<>(Customer.class);
	
	public CustomerForm(MyUI myUI)
	{
		this.myUI = myUI;
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save,delete);
		addComponents(firstname,lastname,email,status,birthday,buttons);
		status.setItems(CustomerStatus.values());
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		binder.bindInstanceFields(this);
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
	}
	
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
		binder.setBean(customer);
		
		delete.setVisible(customer.isPersisted());
		setVisible(true);
		firstname.selectAll();
	}
	
	private void delete()
	{
		service.delete(customer);
		myUI.UpdateList();
		setVisible(false);
	}
	
	private void save()
	{
		service.save(customer);
		myUI.UpdateList();
		setVisible(false);
	}
	
}
