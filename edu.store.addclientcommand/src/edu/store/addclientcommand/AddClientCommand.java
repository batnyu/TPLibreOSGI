package edu.store.addclientcommand;

import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.store.api.ClientStore;

@Component(
	    property= {
	        "osgi.command.scope=edu",
	        "osgi.command.function=addClient"},
	    service=AddClientCommand.class
	)
public class AddClientCommand {
	

    private ComponentFactory factory;
    private ComponentInstance myInstance;
    
	@Reference( 
	target = "(component.factory=edu.store.client.factory)",
	cardinality = ReferenceCardinality.AT_LEAST_ONE, //
	policy = ReferencePolicy.DYNAMIC //
	)
	protected void bind(final ComponentFactory s,
			final Map<String, Object> props) {
		factory = s;
	}
	
	protected void unbind(final ComponentFactory s,
			final Map<String, Object> props) {
		factory = null;
	}

	public void addClient(String aName){
		final Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put("clientName", aName);
		this.myInstance = factory.newInstance(props);
	}
	
    public void test(String target) {
        // create a new service instance
        ComponentInstance instance = this.factory.newInstance(null);
        ClientStore clientStore = (ClientStore) instance.getInstance();
        try {
            //clientStore.shoot(target);
            System.out.println("SALUT JE SUIS UN CLIENT");
        } finally {
            // destroy the service instance
            instance.dispose();
        }
    }
}
