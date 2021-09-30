package Utils;

public enum Cards {
	KREUZ_7(415, 0, 70, 101, 1, "KREUZ", 7),
	KREUZ_8(484, 0, 70, 101, 2, "KREUZ", 8),
	KREUZ_9(553, 0, 70, 101, 3, "KREUZ", 9),
	KREUZ_10(622, 0, 70, 101, 4, "KREUZ", 10),
	KREUZ_11(691, 0, 70, 101, 5, "KREUZ", 11),
	KREUZ_12(760, 0, 70, 101, 6, "KREUZ", 12),
	KREUZ_13(829, 0, 70, 101, 7, "KREUZ", 13),
	KREUZ_14(0, 0, 70, 101, 0, "KREUZ", 14),
	KREUZ_BUBE(KREUZ_11),
	KREUZ_DAME(KREUZ_12),
	KREUZ_KOENIG(KREUZ_13),
	KREUZ_ASS(KREUZ_14),
	
	KARO_7(415, 101, 70, 101, 9, "KARO", 7),
	KARO_8(484, 101, 70, 101, 10, "KARO", 8),
	KARO_9(553, 101, 70, 101, 11, "KARO", 9),
	KARO_10(622, 101, 70, 101, 12, "KARO", 10),
	KARO_11(691, 101, 70, 101, 13, "KARO", 11),
	KARO_12(760, 101, 70, 101, 14, "KARO", 12),
	KARO_13(829, 101, 70, 101, 15, "KARO", 13),
	KARO_14(0, 101, 70, 101, 8, "KARO", 14),
	KARO_BUBE(KARO_11),
	KARO_DAME(KARO_12),
	KARO_KOENIG(KARO_13),
	KARO_ASS(KARO_14),
	
	HERZ_7(415, 201, 70, 101, 17, "HERZ", 7),
	HERZ_8(484, 201, 70, 101, 18, "HERZ", 8),
	HERZ_9(553, 201, 70, 101, 19, "HERZ", 9),
	HERZ_10(622, 201, 70, 101, 20, "HERZ", 10),
	HERZ_11(691, 201, 70, 101, 21, "HERZ", 11),
	HERZ_12(760, 201, 70, 101, 22, "HERZ", 12),
	HERZ_13(829, 201, 70, 101, 23, "HERZ", 13),
	HERZ_14(0, 201, 70, 101, 16, "HERZ", 14),
	HERZ_BUBE(HERZ_11),
	HERZ_DAME(HERZ_12),
	HERZ_KOENIG(HERZ_13),
	HERZ_ASS(HERZ_14),
	
	PIK_7(415, 301, 70, 101, 25, "PIK", 7),
	PIK_8(484, 301, 70, 101, 26, "PIK", 8),
	PIK_9(553, 301, 70, 101, 27, "PIK", 9),
	PIK_10(622, 301, 70, 101, 28, "PIK", 10),
	PIK_11(691, 301, 70, 101, 29, "PIK", 11),
	PIK_12(760, 301, 70, 101, 30, "PIK", 12),
	PIK_13(829, 301, 70, 101, 31, "PIK", 13),
	PIK_14(0, 301, 70, 101, 24, "PIK", 14),
	PIK_BUBE(PIK_11),
	PIK_DAME(PIK_12),
	PIK_KOENIG(PIK_13),
	PIK_ASS(PIK_14),
	
	CARD_BACK(138, 401, 70, 101, 32, "hinten", 0);
	
	int x;
	int y;
	int w;
	int h;
	int pos;
	public String farbe;
	public int nummer;
	
	Cards(Cards card) {
		this.x = card.x;
		this.y = card.y;
		this.w = card.w;
		this.h = card.h;
		this.pos = card.pos;
		this.farbe = card.farbe;
		this.nummer = card.nummer;
	}
	
	Cards(int x, int y, int w, int h, int pos, String farbe, int nummer) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.pos = pos;
		this.farbe = farbe;
		this.nummer = nummer;
	}
	
	public boolean isSpecial() {
		switch(this.nummer) {
		case 7:
		case 8:
		case 9:
		case 11:
			return true;
		default:
			return false;
		}
	}
	
	public boolean fits(Cards drauflegeKarte /*karte die auf this draufgelegt wird*/) {
		if(this.nummer == 11 && drauflegeKarte.nummer == 11) {
			return false;
		}
		if(this.nummer == drauflegeKarte.nummer || this.farbe.equals(drauflegeKarte.farbe) || drauflegeKarte.nummer == 11) {
			return true;
		}
		return false;
	}
	
	public int punkte() {
		switch(this.nummer) {
		case 7:
			return 7;
        case 8:
        	return 8;
        case 9:
        	return 9;
        case 10:
        	return 10;
        case 11:
        	return 20;
        case 12:
        	return 3;
        case 13:
        	return 4;
        case 14:
        	return 11;
        default:
        	return 0;
		}
	}
}
