package com.example.zw.matchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DatingTips extends AppCompatActivity {

    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating_tips);
       // setContentView(R.layout.scrollable_textview);

        mBack = findViewById(R.id.back);

        TextView textView = findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());

        textView.setText("From a new relationship to a good relationship\n" +
                "\n" +
                "In a seasoned relationship, keeping the excitement alive may seem like the biggest bummer. But in a new relationship, it’s learning to hold back the excitement that ends up distancing new lovers.\n" +
                "\n" +
                "If you’ve just met a perfect partner and don’t want to ruin a perfect start, here are all the pointers you need to take it from a new relationship to a good relationship.\n" +
                "\n" +
                "#1 Meet often, but not too often\n" +
                "\n" +
                "When you’re in young love, you’d want to spend every waking minute with your sweetheart. It’s understandable, you’re obviously excited. But could you be pushing it too far?\n" +
                "\n" +
                "Remember that new workout dvd you picked up some time ago? You were probably really excited at the beginning, but as the daily workout took more and more time out of your daily routine, you started getting annoyed by it. It’s the same story with love.\n" +
                "\n" +
                "By meeting too often, you’re suddenly changing the lives of two individuals who have fallen in love. It may feel great for the first week or so, but eventually your other commitments may pile up and one of you may end up getting annoyed with the other for taking too much time.\n" +
                "\n" +
                "Go out on dates once or twice a week, and it’ll keep the love and excitement on a high for a long time. But if you’re both madly in love and can’t keep your hands off each other, then you’re excused to meet each other more often, but with caution.\n" +
                "\n" +
                "#2 Don’t get clingy\n" +
                "\n" +
                "Just because you’re dating doesn’t mean you own each other. Shocking, yes, but it’s true. If you want to know how to have a good relationship from the beginning, learn to give each other space. Especially in a new relationship, you’re only dating each and don’t really need to know every little piece of information about each other. Right now, you’re only a small part of each other’s lives, so don’t give yourself more importance than you deserve.\n" +
                "\n" +
                "#3 Don’t be lavish with your gifts\n" +
                "\n" +
                "Your new lover may be running in your mind all day, but that doesn’t mean you should go overboard and buy something for your lover every time you see something nice while shopping.\n" +
                "\n" +
                "Save the spending sprees for later when the relationship has grown over a solid foundation. If you do want to express your love with gifts, then pick something small, personal and inexpensive at first. Save the extravagant gifts when you know your new mate’s the one for you.\n" +
                "\n" +
                "#4 Don’t push sex in too quickly\n" +
                "\n" +
                "In every new relationship, the horny-o-meter pointer may go into overdrive, just like your love-o-meter. But that doesn’t mean you should try and coerce your partner into having sex with you on the first or second date.\n" +
                "\n" +
                "Take it slow, and if both of you do end up having sex soon, so be it. But don’t try booking a hotel room or ask your new lover to slide over to the back seat for some heavy petting unless it happens without any preplanning. It could make your partner think you’re just in it for the sex and lead to loss of trust.\n" +
                "\n" +
                "#5 Don’t get possessive\n" +
                "\n" +
                "Possessiveness is never a good trait in a relationship. Possessiveness is a sign of insecurity and jealousy, and these are usually big red flags in any relationship, new or old.\n" +
                "\n" +
                "Remember that you’re still in a new relationship and can’t order or even request your mate to avoid people or avoid going out by themselves. Even if you do feel jealous about your lover’s partying habits or the amount of time they spend with a group of friends, learn to suck it up and hold it in. Signs of jealousy and insecurity right at the beginning can end the relationship even before you know it.");

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });
    }
}
