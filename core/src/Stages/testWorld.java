package Stages;

import Characters.Player;
import Environment.testBlocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class testWorld {
	
	Array<testBlocks> blocks = new Array<testBlocks>();
	
	Player player;
	
	public Array<testBlocks> getBlocks(){
		return blocks;
	}
	
	public Player getPlayer(){
		return player;
	}

	public testWorld(){
		createTestWorld();
	}
	
	private void createTestWorld(){
		player = new Player(new Vector2(7,2));
		
		for(int i = 0; i < 16; i++){
			blocks.add(new testBlocks(new Vector2(i, 0)));
			blocks.add(new testBlocks(new Vector2(i, 8)));
			blocks.add(new testBlocks(new Vector2(i, 7)));
			blocks.add(new testBlocks(new Vector2(i, 6)));
			if(i > 2)
			blocks.add(new testBlocks(new Vector2(i,1)));
		}
		blocks.add(new testBlocks(new Vector2(15,2)));
		blocks.add(new testBlocks(new Vector2(15,3)));
		blocks.add(new testBlocks(new Vector2(15,4)));
		blocks.add(new testBlocks(new Vector2(15,5)));
		
		blocks.add(new testBlocks(new Vector2(8,3)));
		blocks.add(new testBlocks(new Vector2(8,4)));
		blocks.add(new testBlocks(new Vector2(8,5)));
	}
}
