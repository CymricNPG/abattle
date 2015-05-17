/**
 * 
 */
package net.npg.abattle.client.view.screens;

import net.npg.abattle.common.utils.Validate;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * @author Cymric
 * 
 */
public class MyChangeListener extends ChangeListener {

	private Procedure2<ChangeEvent, Actor> procedure2;
	private Procedure0 procedure0;

	public MyChangeListener(Procedure2<ChangeEvent, Actor> procedure) {
		Validate.notNull(procedure);
		this.procedure2 = procedure;
	}
	
	public MyChangeListener(Procedure0 procedure) {
		Validate.notNull(procedure);
		this.procedure0 = procedure;
	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		if(procedure2!=null) {
			procedure2.apply(event, actor);
			return;
		}
		if(procedure0!=null) {
			procedure0.apply();
			return;
		}
		
	}

}
