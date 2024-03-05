package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {

	private int health;
	private int speed;
	public Body playerBody;

	Vector2 playerVelocity = new Vector2(0, 0);

	Player(int health, int speed) {
		this.health = health;
		this.speed = speed;
	}

	public void move() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			playerVelocity.y += this.speed;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			playerVelocity.y += -this.speed;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			playerVelocity.x += -this.speed;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			playerVelocity.x += this.speed;
		}

		this.playerBody.setLinearVelocity(this.playerVelocity);

		playerVelocity.x = 0;
		playerVelocity.y = 0;
	}


}
