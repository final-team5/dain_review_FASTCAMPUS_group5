package com.example.finalproject.domain.campaign.dto;

public enum Sns {
	인스타그램(1),
	네이버블로그(2),
	유튜브(3),
	릴스(4),
	쇼츠(5),
	틱톡(6);

	private final int value;

	Sns(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Sns fromValue(int value) {
		for (Sns sns : values()) {
			if (sns.getValue() == value) {
				return sns;
			}
		}
		throw new IllegalArgumentException("Invalid SNS value: " + value);
	}
}
