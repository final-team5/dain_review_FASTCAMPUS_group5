package com.example.finalproject.domain.campaign.dto;

public enum City {
	// 서울특별시
	서울(1),

	// 부산광역시
	부산(2),

	// 대구광역시
	대구(3),

	// 인천광역시
	인천(4),

	// 광주광역시
	광주(5),

	// 대전광역시
	대전(6),

	// 울산광역시
	울산(7),

	// 세종특별자치시
	세종(8),

	경기도(9),

	경남(10),
	경북(11),
	충남(12),
	충북(13),
	전남(14),
	전북(15),
	강원(16),
	제주(17)
	;

	private final int value;

	City(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static City fromValue(int value) {
		for (City city : values()) {
			if (city.getValue() == value) {
				return city;
			}
		}
		throw new IllegalArgumentException("Invalid city value: " + value);
	}
}
