package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;


public class Game extends ApplicationAdapter {


	/*
	 * NOTE: Box2D uses meters as the unit for measurement.
	 * "A common mistake is measuring your world in pixels instead of meters. 
	 * Box2D objects can only travel so fast. If pixels are used (such as 640 by 480) 
	 * instead of meters (such as 12 by 9), objects will always move slowly no matter what you do."
	 */
	World world; 

	OrthographicCamera camera; // Create camera


	// Create debug renderer
	Box2DDebugRenderer debugRenderer;
	

	Vector2 playerVelocity = new Vector2(0, 0);
	Vector2 playerPosition;
	Player player;
	Sprite playerSprite;

	SpriteBatch batch;

	@Override
	public void create () {

		
		// Set the cursor to a crosshair
		Pixmap pixmap = new Pixmap(Gdx.files.internal("Crosshair.png"));
		// Set "hotspot" to the middle of it (0,0 would be the top-left corner)
		int xHotspot = 16, yHotspot = 16;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		pixmap.dispose(); // We don't need the pixmap anymore
		Gdx.graphics.setCursor(cursor);
		
		// Create player
		player = new Player(100, 10);
		


		Box2D.init(); // Initialize Box2D (Physics Engine)
		world = new World(new Vector2(0, 0), true);// Set gravity to 0,0 and allow sleep
		debugRenderer = new Box2DDebugRenderer();
		// camera = new OrthographicCamera(640, 480);
		camera = new OrthographicCamera(32, 18); // Set the camera to 32x18 meters (still 1920x1080 pixels)



		// Create ground
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, -10));

		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();

		groundBox.setAsBox(10, 1);
		groundBody.createFixture(groundBox, 0.0f);

		groundBox.dispose();

		// Create a kinematic body for the player

		BodyDef kinematicBodyDef = new BodyDef();
		kinematicBodyDef.type = BodyDef.BodyType.KinematicBody;
		kinematicBodyDef.position.set(0, 0);

		player.playerBody = world.createBody(kinematicBodyDef);

		PolygonShape kinematicShape = new PolygonShape();
		kinematicShape.setAsBox(0.8f, 1.8f);

		FixtureDef kinematicFixtureDef = new FixtureDef();
		kinematicFixtureDef.shape = kinematicShape;
		kinematicFixtureDef.density = 1.0f;

		player.playerBody.createFixture(kinematicFixtureDef);

		kinematicShape.dispose();

		batch = new SpriteBatch();

		Texture player_texture = new Texture("Test_Player.png");
		playerSprite = new Sprite(player_texture);
		playerSprite.setSize(1.6f, 3.6f);

	}

	
	
	static final float PHYSICS_FPS = 165;
	float accumulator = 0;

	private void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);
	
		if (accumulator >= 1/PHYSICS_FPS) {
			accumulator -= 1/PHYSICS_FPS;
			world.step(1/PHYSICS_FPS, 6, 2);
		}
	}


	@Override
	public void render () {
		ScreenUtils.clear(0.05f, 0.05f, 0.05f, 1);


		debugRenderer.render(world, camera.combined);
		
		// world.step(1/165f, 6, 2);
		stepWorld(); // Ensure that the physics engine is updated at a fixed rate (60fps in this case)
		
		player.move(); // Move the player every frame
		playerSprite.setPosition(player.playerBody.getPosition().x-0.8f, player.playerBody.getPosition().y-1.8f);
		camera.position.set(player.playerBody.getPosition().x, player.playerBody.getPosition().y, 0);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		playerSprite.draw(batch);
		batch.end();
		
		// Update the camera to follow the player

		camera.update();

	}
	
	@Override
	public void dispose () {
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();

	}
}
