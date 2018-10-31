package Sounds;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Jukebox {

	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	
	public static void load() {
		try {
			soundMap.put("alien_death", new Sound("res/aliendeath.wav"));
			soundMap.put("destroyed", new Sound("res/buildingdestroyed.wav"));
			soundMap.put("buy", new Sound("res/buy.wav"));
			soundMap.put("grass_step", new Sound("res/grassstep.wav"));
			soundMap.put("gun", new Sound("res/gun.wav"));
			soundMap.put("hurt", new Sound("res/hurt.wav"));
			soundMap.put("player_death", new Sound("res/playerdeath.wav"));
			soundMap.put("select", new Sound("res/select.wav"));
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
