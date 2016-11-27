package Assignment_2;

 /* Basic implementation of a skin */

import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;

public class CustomControlSkin extends SkinBase<CustomControl> implements
Skin<CustomControl> {

	public CustomControlSkin(CustomControl cc) {
		super(cc);
	}
}
