package com.example.finalproject.domain.campaign.dto;

public enum Category {
	맛집(1),
	뷰티(2),
	여행(3),
	문화(4),
	식품(5),
	생활(6),
	디지털(7);

	private final int value;

	Category(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Category fromValue(int value) {
		for (Category category : values()) {
			if (category.getValue() == value) {
				return category;
			}
		}
		throw new IllegalArgumentException("Invalid category value: " + value);
	}
}
