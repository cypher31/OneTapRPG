package Stages;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class tiledTest extends ApplicationAdapter{

	private TiledMap map;
	private float tileWidth;
	private float tileHeight;
	
	public tiledTest(String tilemapName){
		
		map = new TmxMapLoader().load("testLevel.tmx");
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
		tileWidth = layer.getTileWidth();
		tileHeight = layer.getTileHeight();
		
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public float getTileHeight(){
		return tileHeight;
	}
	
	public float getTileWidth(){
		return tileWidth;
	}
	
}