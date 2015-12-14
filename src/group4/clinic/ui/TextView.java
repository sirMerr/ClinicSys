/**
 * 
 */
package group4.clinic.ui;

import group4.clinic.business.Clinic;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * @author Tiffany
 *
 */
public class TextView implements Observer {

	private Clinic obj;
	public TextView(Clinic obj)
	{
		this.obj = obj;
		obj.addObserver(this);
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1.getClass().equals(Optional.class))
		{
			
		}
		
	}

}
