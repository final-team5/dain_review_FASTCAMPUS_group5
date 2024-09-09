package com.example.finalproject.domain.campaign.dto;

public enum Type {
	방문형(1),
	구매형(2),
	배송형(3),
	기자단(4),
	포장형(5);

	private final int value;

	Type(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Type fromValue(int value) {
		for (Type type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid type value: " + value);
	}
}
