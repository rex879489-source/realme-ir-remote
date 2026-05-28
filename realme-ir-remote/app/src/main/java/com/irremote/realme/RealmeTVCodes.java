package com.irremote.realme;

/**
 * NEC IR codes for Realme Smart TV (32"/43"/55")
 * Frequency: 38000 Hz
 * Format: NEC protocol - pulse/space pairs in microseconds
 *
 * These are standard Realme TV codes. If they don't work,
 * see README for how to learn codes from original remote.
 */
public class RealmeTVCodes {

    public static final int CARRIER_FREQUENCY = 38000; // 38 kHz

    // NEC protocol helper — builds pulse/space array from 32-bit NEC code
    // NEC: 9000us lead pulse, 4500us lead space, then 32 bits
    // bit 0 = 562us pulse + 562us space
    // bit 1 = 562us pulse + 1687us space
    // trailing 562us pulse
    public static int[] nec(int address, int command) {
        // address = 8-bit device address, command = 8-bit command
        int[] pattern = new int[2 + 32 * 2 + 1]; // leader(2) + 32bits(64) + stop(1)
        int idx = 0;

        // Leader
        pattern[idx++] = 9000;
        pattern[idx++] = 4500;

        // Encode 32 bits: address, ~address, command, ~command
        int data = (address & 0xFF)
                | ((~address & 0xFF) << 8)
                | ((command & 0xFF) << 16)
                | ((~command & 0xFF) << 24);

        for (int i = 0; i < 32; i++) {
            pattern[idx++] = 562; // pulse
            if ((data & (1 << i)) != 0) {
                pattern[idx++] = 1687; // bit 1
            } else {
                pattern[idx++] = 562;  // bit 0
            }
        }

        // Stop bit
        pattern[idx] = 562;
        return pattern;
    }

    // ── Realme TV IR Codes (NEC protocol, address = 0x04) ──────────────────
    // Address byte for Realme Smart TV
    private static final int ADDR = 0x04;

    public static int[] POWER      = nec(ADDR, 0x08);
    public static int[] MUTE       = nec(ADDR, 0x0C);

    public static int[] VOL_UP     = nec(ADDR, 0x10);
    public static int[] VOL_DOWN   = nec(ADDR, 0x11);

    public static int[] CH_UP      = nec(ADDR, 0x1E);
    public static int[] CH_DOWN    = nec(ADDR, 0x1F);

    public static int[] UP         = nec(ADDR, 0x52);
    public static int[] DOWN       = nec(ADDR, 0x53);
    public static int[] LEFT       = nec(ADDR, 0x51);
    public static int[] RIGHT      = nec(ADDR, 0x50);
    public static int[] OK         = nec(ADDR, 0x57);

    public static int[] HOME       = nec(ADDR, 0x61);
    public static int[] BACK       = nec(ADDR, 0x58);
    public static int[] MENU       = nec(ADDR, 0x5B);

    public static int[] SOURCE     = nec(ADDR, 0x0B);
    public static int[] INFO       = nec(ADDR, 0x4C);

    public static int[] NUM_0      = nec(ADDR, 0x00);
    public static int[] NUM_1      = nec(ADDR, 0x01);
    public static int[] NUM_2      = nec(ADDR, 0x02);
    public static int[] NUM_3      = nec(ADDR, 0x03);
    public static int[] NUM_4      = nec(ADDR, 0x04);
    public static int[] NUM_5      = nec(ADDR, 0x05);
    public static int[] NUM_6      = nec(ADDR, 0x06);
    public static int[] NUM_7      = nec(ADDR, 0x07);
    public static int[] NUM_8      = nec(ADDR, 0x09);
    public static int[] NUM_9      = nec(ADDR, 0x0A);

    public static int[] RED        = nec(ADDR, 0x44);
    public static int[] GREEN      = nec(ADDR, 0x45);
    public static int[] YELLOW     = nec(ADDR, 0x46);
    public static int[] BLUE       = nec(ADDR, 0x47);

    public static int[] PLAY_PAUSE = nec(ADDR, 0x30);
    public static int[] STOP       = nec(ADDR, 0x31);
    public static int[] REW        = nec(ADDR, 0x32);
    public static int[] FF         = nec(ADDR, 0x33);
}
