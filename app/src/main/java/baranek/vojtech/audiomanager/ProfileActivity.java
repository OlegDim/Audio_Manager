package baranek.vojtech.audiomanager;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.MaterialDialog;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileActivity extends AppCompatActivity implements ProfileActivityView{


    //-------Initialize components------//


    @Bind(R.id.et_TimerName)
    EditText etNazevCasovace;

    //---Zacatecni nastaveni---//
    @Bind(R.id.tvZacCas)
    TextView tvZacCas;
    @Bind(R.id.tvZacRez) TextView tvZacRez;
    @Bind(R.id.cvZacHlasitost)
    CardView cvZacHlasitost;
    @Bind(R.id.sliderZacAlarm)
    DiscreteSeekBar sliderZacAlarm;
    @Bind(R.id.sliderZacMedia) DiscreteSeekBar sliderZacMedia;
    @Bind(R.id.sliderZacVyzvan) DiscreteSeekBar sliderZacVyzvan;

    //---Konecne nastaveni---//
    @Bind(R.id.tvKonCas) TextView tvKonCas;
    @Bind(R.id.tvKonRez) TextView tvKonRez;
    @Bind(R.id.cvKonHlasitost) CardView cvKonHlasitost;
    @Bind(R.id.sliderKonAlarm) DiscreteSeekBar sliderKonAlarm;
    @Bind(R.id.sliderKonMedia) DiscreteSeekBar sliderKonMedia;
    @Bind(R.id.sliderKonVyzvan) DiscreteSeekBar sliderKonVyzvan;
    @Bind(R.id.chbKonec)
    CheckBox chbKonecAktiv;

    //---Opakokvani---//
    @Bind(R.id.tgbPo)
    ToggleButton tgbPo;
    @Bind(R.id.tgbUt) ToggleButton tgbUt;
    @Bind(R.id.tgbSt) ToggleButton tgbSt;
    @Bind(R.id.tgbCt) ToggleButton tgbCt;
    @Bind(R.id.tgbPa) ToggleButton tgbPa;
    @Bind(R.id.tgbSo) ToggleButton tgbSo;
    @Bind(R.id.tgbNe) ToggleButton tgbNe;


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
         setToolbar();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void getKonZapFromChBoxandName() {

        timer.setKonZap(chbKonecAktiv.isChecked());
        timer.setNazev(etNazevCasovace.getText().toString());

    }

    private void getDaysFromTgbs() {

        ProfileSetters.getDaysFromTgbs(tgbPo,tgbUt,tgbSt,tgbCt,tgbPa,tgbSo,tgbNe,timer);
    }

    private void getVolumeFromSliders() {
        ProfileSetters.getVolumeFromSliders(sliderZacAlarm, sliderZacMedia, sliderZacVyzvan, timer, true);
        ProfileSetters.getVolumeFromSliders(sliderKonAlarm, sliderKonMedia, sliderKonVyzvan, timer, false);

    }


    //-- Zacatek time settings --//
    @OnClick(R.id.tvZacCas) void zacTimeSet(){
        ProfileSetters.getTimeFromTimePicker(getFragmentManager(), timer, true, tvZacCas);
    }

    //    Konec time settings //
    @OnClick(R.id.tvKonCas) void konTimeSet(){
        ProfileSetters.getTimeFromTimePicker(getFragmentManager(), timer, false, tvKonCas);
    }

    //    Select Zacatecni rezim   //
    @OnClick(R.id.tvZacRez) void setTvZacRez(){
        new MaterialDialog.Builder(this)
                .title(R.string.select_mode_dialo_title)
                .items(R.array.sound_modes)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        timer.setZacRez(i);
                        ProfileSetters.setTvRezimAndShowCardView(timer.getZacRez(),tvZacRez,cvZacHlasitost);
                        return false;
                    }
                })
                .positiveText("Ok")
                .show();

    }

    //    Select koncovy rezim   //
    @OnClick(R.id.tvKonRez) void setTvKonRez(){
        new MaterialDialog.Builder(this)
                .title(R.string.select_mode_dialo_title)
                .items(R.array.sound_modes)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        timer.setKonRez(i);
                        ProfileSetters.setTvRezimAndShowCardView(timer.getKonRez(),tvKonRez,cvKonHlasitost);
                        return false;
                    }
                })
                .positiveText("Ok")
                .show();

    }



    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout cv = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        cv.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // clear FLAG_TRANSLUCENT_STATUS flag:

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void showData(TimerProfile timerProfile) {

    }

    @Override
    public void setSeekersRange(int max) {

    }
}
