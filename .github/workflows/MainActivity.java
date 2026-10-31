package com.capri.companion;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
 final int bg=Color.rgb(12,10,25), pink=Color.rgb(255,76,184), cyan=Color.rgb(76,227,240);
 LinearLayout root, log; TextView mood, affection; EditText input;
 int hearts=0; Random rng=new Random(); HashMap<String,String[]> lines=new HashMap<>();
 String[] starts={"Знаешь, ","Хм... ","Слушай, ","Эй, ","Интересно... ","Не ожидала, но ","Погоди, ","Я тут подумала: ","Между прочим, ","Если честно, "};
 String[] middles={"ты заставляешь меня улыбаться", "в Найт-Сити сегодня неспокойно", "мне нравится твоя компания", "у меня есть одна история", "пора бы немного отдохнуть", "этот вечер обещает быть интересным", "ты умеешь удивлять", "нам стоит прогуляться", "мне хочется послушать музыку", "я рада, что ты здесь", "хочу узнать тебя получше", "я сегодня в хорошем настроении", "у тебя отличный вкус", "не люблю скучные разговоры", "ты снова меня смутил", "давай придумаем приключение", "хочу показать тебе ночной город", "с тобой время летит быстрее", "неожиданно приятно тебя видеть", "я доверяю тебе всё больше"};
 String[] ends={".","!","..."," — правда?"," ♡"," Как думаешь?"," Только никому не говори."," Вот такие дела."," А ты что скажешь?"," Не смейся!"};
 @Override public void onCreate(Bundle b){super.onCreate(b); hearts=getPreferences(0).getInt("hearts",0); seed(); home();}
 void seed(){
 lines.put("Поговорить",new String[]{"Ну, рассказывай. Как прошёл твой день?","Я слушаю. Только не начинай со скучных подробностей!","В Найт-Сити тишина — подозрительная штука.","Давай поболтаем. Мне нравится твой голос.","Сегодня у меня есть время только для тебя."});
 lines.put("Обнять",new String[]{"Эй... неожиданно. Но приятно.","Можно ещё немного так постоять?","Ты тёплый. Не уходи пока.","Ладно, обниму в ответ. Только не привыкай!"});
 lines.put("Погладить",new String[]{"М-м... это приятно.","Не растрепли мне волосы!","Ладно, можешь продолжать.","Ты решил меня приручить?"});
 lines.put("Подарок",new String[]{"Это мне? Серьёзно? Спасибо!","Вау. Ты запомнил, что мне нравится?","Буду беречь. Обещаю.","У тебя талант делать сюрпризы!"});
 lines.put("Флирт",new String[]{"Хм, умеешь подбирать слова...","Ты это специально, чтобы я покраснела?","Продолжай. Мне любопытно.","Опасная игра, знаешь ли ♡"});
 lines.put("Прогулка",new String[]{"Поехали смотреть неоновые огни!","Я знаю тихое место над городом.","Только держись рядом, хорошо?","Ночной город красивее, когда мы вдвоём."});
 lines.put("Музыка",new String[]{"Поставь что-нибудь с мощным басом!","У меня есть любимый плейлист.","Танцуем? Только не наступай на ноги!"});
 lines.put("Игра",new String[]{"На что играем?","Я тебя обыграю, готовься!","Ну что, ещё один раунд?","Эй, нечестно! Ты слишком хорош."});
 lines.put("Отдых",new String[]{"Давай просто посидим рядом.","Наконец-то немного покоя.","Можно помолчать вместе. Мне нравится."});
 }
 GradientDrawable shape(int color,int stroke){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(22);d.setStroke(2,stroke);return d;}
 TextView label(String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setPadding(14,12,14,12);return t;}
 void init(String title){ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(1);root.setPadding(20,24,20,24);root.setBackgroundColor(bg);sc.addView(root);setContentView(sc);TextView h=label(title,26,pink);h.setTypeface(null,Typeface.BOLD);root.addView(h);}
 void home(){init("КАПРИ  //  NIGHT CITY"); TextView face=label("✦  ◉  ♡  ✦\n\nКАПРИ\nТвоя спутница в неоновом городе",28,cyan);face.setGravity(Gravity.CENTER);face.setBackground(shape(0xff21142e,pink));root.addView(face);affection=label("♡ Симпатия: "+hearts,20,pink);root.addView(affection);mood=label("Настроение: "+(hearts>100?"нежная":hearts>30?"дружелюбная":"дерзкая"),17,cyan);root.addView(mood);button("Общаться",()->chat());button("Взаимодействия",()->actions());button("Случайная фраза",()->reply(randomLine()));button("О приложении",()->about());}
 void button(String title,Runnable action){Button b=new Button(this);b.setText(title);b.setTextSize(17);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setBackground(shape(0xff321a3e,pink));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,8,0,8);root.addView(b,p);b.setOnClickListener(v->action.run());}
 void actions(){init("ВЗАИМОДЕЙСТВИЯ");button("← Назад",()->home());for(String key:lines.keySet())button(key,()->act(key));}
 void act(String key){hearts+=key.equals("Флирт")?3:2;getPreferences(0).edit().putInt("hearts",hearts).apply();String[] opts=lines.get(key);reply(opts[rng.nextInt(opts.length)]);}
 String randomLine(){return starts[rng.nextInt(starts.length)]+middles[rng.nextInt(middles.length)]+ends[rng.nextInt(ends.length)];}
 void reply(String s){new AlertDialog.Builder(this).setTitle("Капри ♡").setMessage(s).setPositiveButton("Ещё",(d,w)->reply(randomLine())).setNegativeButton("Закрыть",null).show();}
 void chat(){init("ЧАТ С КАПРИ");button("← Назад",()->home());log=new LinearLayout(this);log.setOrientation(1);root.addView(log);addMessage("Капри","Эй, привет! О чём поговорим?",pink);input=new EditText(this);input.setHint("Напиши Капри...");input.setHintTextColor(0xffaaaaaa);input.setTextColor(Color.WHITE);input.setSingleLine(false);root.addView(input);button("Отправить",()->{String s=input.getText().toString().trim();if(s.isEmpty())return;addMessage("Ты",s,cyan);input.setText("");addMessage("Капри",answer(s),pink);});button("Обнять ♡",()->{act("Обнять");addMessage("Капри",lines.get("Обнять")[rng.nextInt(lines.get("Обнять").length)],pink);});}
 void addMessage(String who,String s,int color){TextView t=label(who+": "+s,17,color);t.setBackground(shape(0xff201a32,0xff46335c));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,5,0,5);log.addView(t,p);}
 String answer(String s){String q=s.toLowerCase(new Locale("ru"));if(q.contains("привет")||q.contains("здравств"))return "Привет! Я рада тебя видеть ♡";if(q.contains("люблю")||q.contains("нравишься"))return hearts>20?"Ты мне тоже очень дорог ♡":"Ого, какие слова... Ты меня смутил!";if(q.contains("груст")||q.contains("плох"))return "Эй, я рядом. Хочешь поговорить об этом?";if(q.contains("как дела")||q.contains("как ты"))return "Лучше, когда ты здесь. А у тебя?";if(q.contains("спокойной ночи"))return "Сладких снов. Увидимся завтра ♡";if(q.contains("имя")||q.contains("зовут"))return "Я Капри. И ты это прекрасно знаешь!";if(q.contains("спасибо"))return "Всегда пожалуйста ♡";return randomLine();}
 void about(){init("О ПРИЛОЖЕНИИ");root.addView(label("Капри — офлайн-компаньон в киберпанк-стиле. 2000+ комбинаций реплик, 9 действий, система симпатии и локальный чат с шаблонными ответами. Без интернета и регистрации. Это не ИИ-чат и не официальный продукт Cyberpunk.",17,Color.WHITE));button("← Назад",()->home());}
 @Override public void onBackPressed(){home();}
}
