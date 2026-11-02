package id.alpin67.kopscrash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(10, 7, 18);
    private final int panel = Color.rgb(27, 17, 43);
    private final int purple = Color.rgb(190, 70, 255);
    private final Handler handler = new Handler(Looper.getMainLooper());
    private LinearLayout content;
    private EditText messageInput;
    private SeekBar delaySeek;
    private TextView delayValue;
    private boolean groupMode = false;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        showSplash();
    }

    private void showSplash() {
        LinearLayout splash = new LinearLayout(this);
        splash.setOrientation(LinearLayout.VERTICAL);
        splash.setGravity(Gravity.CENTER);
        splash.setBackgroundColor(bg);
        AnimeView anime = new AnimeView(this);
        splash.addView(anime, new LinearLayout.LayoutParams(-1, 0, 1));
        TextView title = label("KOPS CRASH", 34, purple, true);
        splash.addView(title);
        TextView sub = label("ANIME CYBER • ALPIN67", 12, Color.WHITE, false);
        splash.addView(sub);
        TextView loading = label("INITIALIZING", 12, Color.LTGRAY, false);
        splash.addView(loading);
        setContentView(splash);
        AlphaAnimation pulse = new AlphaAnimation(0.35f, 1f);
        pulse.setDuration(850);
        pulse.setRepeatMode(Animation.REVERSE);
        pulse.setRepeatCount(Animation.INFINITE);
        title.startAnimation(pulse);
        handler.postDelayed(this::showDashboard, 1800);
    }

    private void showDashboard() {
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);
        base.setBackgroundColor(bg);

        LinearLayout header = new LinearLayout(this);
        header.setPadding(20, 22, 20, 16);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setBackgroundColor(Color.rgb(18, 10, 29));
        TextView brand = label("♛  KOPS CRASH", 23, purple, true);
        header.addView(brand, new LinearLayout.LayoutParams(0, -2, 1));
        TextView version = label("v1.0", 12, Color.LTGRAY, false);
        header.addView(version);
        base.addView(header);

        AnimeView banner = new AnimeView(this);
        base.addView(banner, new LinearLayout.LayoutParams(-1, 175));

        LinearLayout tabs = new LinearLayout(this);
        tabs.setPadding(14, 10, 14, 8);
        Button personal = button("PERSONAL");
        Button groups = button("GRUP");
        tabs.addView(personal, new LinearLayout.LayoutParams(0, 48, 1));
        tabs.addView(groups, new LinearLayout.LayoutParams(0, 48, 1));
        base.addView(tabs);

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setPadding(16, 8, 16, 28);
        content.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(content);
        base.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        personal.setOnClickListener(v -> {
            groupMode = false;
            personal.setAlpha(1f); groups.setAlpha(0.55f);
            renderFeatures();
        });
        groups.setOnClickListener(v -> {
            groupMode = true;
            groups.setAlpha(1f); personal.setAlpha(0.55f);
            renderFeatures();
        });

        setContentView(base);
        personal.setAlpha(1f);
        groups.setAlpha(0.55f);
        renderFeatures();
    }

    private void renderFeatures() {
        content.removeAllViews();
        addSection(groupMode ? "FITUR GRUP" : "FITUR PERSONAL");
        addCard("Sender Global", "Bagikan pesan secara manual melalui aplikasi pilihan", this::composeShare);
        addCard("Kirim Tes WhatsApp", "Masukkan nomor dan buka chat dengan pesan tes yang aman", this::sendWhatsAppTest);
        addCard("Delay", "Atur jeda sebelum lembar berbagi dibuka", this::showDelay);
        if (groupMode) {
            addCard("Moderasi Grup", "Panduan pengelolaan anggota dan aturan grup", this::showModeration);
            addCard("Status Grup", "Catatan status dan informasi grup", this::showStatusInfo);
        } else {
            addCard("Force Close Test", "Simulasi layar uji stabilitas di dalam KOPS CRASH", this::showStabilityTest);
            addCard("Blank Screen FX", "Animasi layar gelap/glitch di dalam aplikasi ini", this::showBlankFx);
            addCard("Status Akun", "Informasi pemeriksaan status akun WhatsApp", this::showStatusInfo);
        }
        addSection("PENGATURAN");
        addCard("Tentang KOPS CRASH", "Versi 1.0 • Anime Cyber UI • Firebase belum diaktifkan", () ->
            new AlertDialog.Builder(this).setTitle("KOPS CRASH")
                .setMessage("Aplikasi berbagi pesan manual dan simulasi efek visual lokal. Tidak mengirim otomatis, tidak mengganggu aplikasi lain, dan tidak melakukan pemblokiran akun.")
                .setPositiveButton("TUTUP", null).show());
    }

    private void addSection(String text) {
        TextView t = label(text, 13, purple, true);
        t.setPadding(4, 15, 4, 8);
        content.addView(t);
    }

    private void addCard(String title, String desc, Runnable action) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16, 13, 16, 13);
        card.setBackground(roundDrawable(panel, 22));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.bottomMargin = 10;
        content.addView(card, lp);
        TextView h = label("✦  " + title, 17, Color.WHITE, true);
        TextView d = label(desc, 12, Color.LTGRAY, false);
        d.setPadding(29, 5, 2, 0);
        card.addView(h);
        card.addView(d);
        card.setOnClickListener(v -> action.run());
    }

    private void composeShare() {
        LinearLayout box = new LinearLayout(this);
        box.setPadding(20, 8, 20, 4);
        box.setOrientation(LinearLayout.VERTICAL);
        messageInput = new EditText(this);
        messageInput.setTextColor(Color.WHITE);
        messageInput.setHintTextColor(Color.GRAY);
        messageInput.setHint("Tulis pesan untuk dibagikan");
        messageInput.setMinLines(3);
        box.addView(messageInput);
        new AlertDialog.Builder(this).setTitle(groupMode ? "Sender Global • Grup" : "Sender Global • Personal")
            .setView(box).setNegativeButton("BATAL", null)
            .setPositiveButton("BAGIKAN", (d, w) -> {
                String msg = messageInput.getText().toString().trim();
                if (msg.isEmpty()) {
                    Toast.makeText(this, "Pesan masih kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                int delay = delaySeek == null ? 0 : delaySeek.getProgress();
                handler.postDelayed(() -> {
                    Intent send = new Intent(Intent.ACTION_SEND);
                    send.setType("text/plain");
                    send.putExtra(Intent.EXTRA_TEXT, msg);
                    startActivity(Intent.createChooser(send, "Bagikan pesan"));
                }, delay * 1000L);
                Toast.makeText(this, "Lembar berbagi dibuka setelah " + delay + " detik", Toast.LENGTH_SHORT).show();
            }).show();
    }


    private void sendWhatsAppTest() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(20, 8, 20, 4);

        EditText numberInput = new EditText(this);
        numberInput.setTextColor(Color.WHITE);
        numberInput.setHintTextColor(Color.GRAY);
        numberInput.setHint("Nomor WA, contoh 628123456789");
        numberInput.setInputType(3);
        box.addView(numberInput);

        TextView note = label("Pesan tes aman: “Halo, ini pesan uji dari KOPS CRASH.”", 12, Color.LTGRAY, false);
        note.setPadding(0, 10, 0, 0);
        box.addView(note);

        new AlertDialog.Builder(this)
            .setTitle("KIRIM PESAN TES")
            .setView(box)
            .setNegativeButton("BATAL", null)
            .setPositiveButton("BUKA WHATSAPP", (dialog, which) -> {
                String number = numberInput.getText().toString().replaceAll("[^0-9]", "");
                if (number.length() < 8 || number.length() > 15) {
                    Toast.makeText(this, "Periksa nomor WA (gunakan kode negara, misalnya 62)", Toast.LENGTH_LONG).show();
                    return;
                }
                String safeMessage = "Halo, ini pesan uji dari KOPS CRASH.";
                String url = "https://wa.me/" + number + "?text=" + Uri.encode(safeMessage);
                try {
                    Intent openChat = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(openChat);
                    Toast.makeText(this, "Chat WhatsApp dibuka. Tekan KIRIM di WhatsApp untuk mengirim pesan.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "WhatsApp atau browser tidak dapat dibuka.", Toast.LENGTH_LONG).show();
                }
            }).show();
    }

    private void showDelay() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(24, 12, 24, 8);
        delayValue = label("Jeda: " + (delaySeek == null ? 0 : delaySeek.getProgress()) + " detik", 16, Color.WHITE, true);
        SeekBar seek = new SeekBar(this);
        seek.setMax(60);
        seek.setProgress(delaySeek == null ? 0 : delaySeek.getProgress());
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar s, int p, boolean user) {
                delayValue.setText("Jeda: " + p + " detik");
                delaySeek = s;
            }
            public void onStartTrackingTouch(SeekBar s) {}
            public void onStopTrackingTouch(SeekBar s) {}
        });
        box.addView(delayValue);
        box.addView(seek);
        new AlertDialog.Builder(this).setTitle("PENGATURAN DELAY").setView(box)
            .setPositiveButton("SIMPAN", (d, w) -> delaySeek = seek)
            .setNegativeButton("TUTUP", null).show();
    }

    private void showStabilityTest() {
        new AlertDialog.Builder(this).setTitle("STABILITY TEST")
            .setMessage("Simulasi uji stabilitas lokal. Tombol ini hanya menampilkan panel hasil dan tidak menutup paksa WhatsApp atau aplikasi lain.")
            .setPositiveButton("JALANKAN", (d, w) -> {
                Toast.makeText(this, "KOPS CRASH: simulasi selesai • 0 aplikasi eksternal dihentikan", Toast.LENGTH_LONG).show();
            }).setNegativeButton("BATAL", null).show();
    }

    private void showBlankFx() {
        View blank = new View(this);
        blank.setBackgroundColor(Color.BLACK);
        setContentView(blank);
        blank.animate().alpha(0.2f).setDuration(350).withEndAction(() ->
            blank.animate().alpha(1f).setDuration(350).withEndAction(this::showDashboard).start()).start();
        handler.postDelayed(this::showDashboard, 1500);
    }

    private void showModeration() {
        new AlertDialog.Builder(this).setTitle("MODERASI GRUP")
            .setMessage("Panel moderasi: tetapkan admin, aturan grup, persetujuan anggota, dan laporkan spam melalui fitur resmi WhatsApp. KOPS CRASH tidak memblokir atau mengeluarkan anggota secara otomatis.")
            .setPositiveButton("MENGERTI", null).show();
    }

    private void showStatusInfo() {
        new AlertDialog.Builder(this).setTitle("STATUS & AKUN")
            .setMessage("KOPS CRASH tidak dapat membaca status larangan akun WhatsApp. Gunakan pemeriksaan resmi di aplikasi WhatsApp dan Pusat Bantuan.")
            .setPositiveButton("TUTUP", null).show();
    }

    private TextView label(String text, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setTypeface(Typeface.create("sans-serif", bold ? Typeface.BOLD : Typeface.NORMAL));
        return t;
    }

    private Button button(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setBackground(roundDrawable(Color.rgb(91, 25, 145), 18));
        return b;
    }

    private android.graphics.drawable.GradientDrawable roundDrawable(int color, int radius) {
        android.graphics.drawable.GradientDrawable d = new android.graphics.drawable.GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius * getResources().getDisplayMetrics().density);
        d.setStroke((int)(1.2f * getResources().getDisplayMetrics().density), purple);
        return d;
    }

    private class AnimeView extends View {
        private final Paint p = new Paint(3);
        private long start = System.currentTimeMillis();
        AnimeView(Activity a) { super(a); setLayerType(View.LAYER_TYPE_SOFTWARE, null); }
        @Override protected void onDraw(Canvas c) {
            super.onDraw(c);
            float w = getWidth(), h = getHeight();
            p.setShader(new LinearGradient(0, 0, w, h, new int[]{Color.rgb(18,5,31), Color.rgb(55,10,85), Color.rgb(8,7,18)}, null, Shader.TileMode.CLAMP));
            c.drawRect(0,0,w,h,p); p.setShader(null);
            float phase = ((System.currentTimeMillis()-start)%2400)/2400f;
            float cx=w/2f, cy=h*.42f;
            p.setColor(Color.argb(35,210,70,255)); p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(3);
            c.drawCircle(cx,cy,Math.min(w,h)*(.27f+.025f*(float)Math.sin(phase*6.28)),p);
            p.setStyle(Paint.Style.FILL);
            // Stylized anime silhouette: hair, face, collar and glowing eyes.
            p.setColor(Color.rgb(20,14,34));
            c.drawOval(new RectF(cx-w*.19f,cy-h*.25f,cx+w*.19f,cy+h*.27f),p);
            p.setColor(Color.rgb(225,215,245));
            c.drawOval(new RectF(cx-w*.115f,cy-h*.17f,cx+w*.115f,cy+h*.15f),p);
            p.setColor(Color.rgb(42,22,68));
            PathHelper.drawHair(c,p,cx,cy,w,h);
            p.setColor(Color.rgb(194,55,255));
            c.drawOval(new RectF(cx-w*.075f,cy-h*.005f,cx-w*.018f,cy+h*.025f),p);
            c.drawOval(new RectF(cx+w*.018f,cy-h*.005f,cx+w*.075f,cy+h*.025f),p);
            p.setColor(Color.rgb(10,8,18));
            c.drawOval(new RectF(cx-w*.16f,cy+h*.17f,cx+w*.16f,cy+h*.49f),p);
            p.setColor(purple); p.setTextAlign(Paint.Align.CENTER); p.setTypeface(Typeface.create("sans-serif-black",Typeface.BOLD));
            p.setTextSize(Math.max(22,w*.075f)); c.drawText("KOPS CRASH",cx,h*.88f,p);
            p.setTextSize(10); p.setColor(Color.WHITE); c.drawText("ANIME CYBER • ALPIN67",cx,h*.96f,p);
            postInvalidateDelayed(45);
        }
    }

    private static class PathHelper {
        static void drawHair(Canvas c, Paint p, float x, float y, float w, float h) {
            android.graphics.Path path = new android.graphics.Path();
            path.moveTo(x-w*.16f,y-h*.06f);
            path.lineTo(x-w*.24f,y-h*.29f);
            path.lineTo(x-w*.07f,y-h*.23f);
            path.lineTo(x,y-h*.34f);
            path.lineTo(x+w*.08f,y-h*.23f);
            path.lineTo(x+w*.23f,y-h*.29f);
            path.lineTo(x+w*.17f,y-h*.02f);
            path.close();
            c.drawPath(path,p);
        }
    }
}
