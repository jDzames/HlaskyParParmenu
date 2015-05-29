package s.ics.upjs.sk.jdzama.hlaskyparparmenu;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jDzama on 18.5.2015.
 */

public enum HlaskyList {
    INSTANCE;

    public final Song[] hlaskyBobromil = {new Song(R.raw.bobromilveelky, "Bobromil veelky", "Bobromil"),
            new Song(R.raw.mracit, "Ale teď se budu mračit", "Bobromil"),
            new Song(R.raw.parenivnadoru, "Pařeni v Nádoru", "Bobromil"),
            new Song(R.raw.prdnul, "Pardon, asi sem si prdnul", "Bobromil"),
            new Song(R.raw.kuchardebilvestec, "Už nám chýba jenom kuchař, debil a věštec", "Bobromil"),
            new Song(R.raw.ucenisermovat, "Ja vás naučím šermovat", "Bobromil"),
            new Song(R.raw.stiskatpoklobouku, "Stískat po Špicatém Klobouku", "Bobromil"),
            new Song(R.raw.brzoumru, "Brzo umru", "Bobromil"),
            new Song(R.raw.chytejnetrefil, "Tak tos posral kámo, chytej", "Bobromil"),
            new Song(R.raw.cosenelibinaklackech, "Co se ti na klackech nelíbi", "Bobromil"),
            new Song(R.raw.jsemtlustej, "To jako že jsem tlustej", "Bobromil"),
            new Song(R.raw.jakzijes, "Jak žiješ", "Hobitofil ft. Bobromil"),
            new Song(R.raw.kdybychtesjedli, "Kdybych te sjedli", "Hobitofil ft. Bobromil"),
            new Song(R.raw.sipyzhecu, "Šípy z hecu", "Bobromil ft. Hobitofil")
    };
    public final Song[] hlaskyFritol = {new Song(R.raw.kdeseflakas, "Kde se flákaš", "Fritol"),
            new Song(R.raw.smiech, "Hehehuhu", "Fritol ft. Šmajdalf"),
            new Song(R.raw.nakupysmajdalfa, "Nákupy Šmajdalfa", "Fritol ft. Šmajdalf"),
            new Song(R.raw.nejsemtaknenazrany, "Nejsem tak nenažraný", "Fritol"),
            new Song(R.raw.hnusnipocasi, "Hnusní počasí", "Fritol"),
            new Song(R.raw.clovekbezmozgu, "Pivo", "Fritol"),
            new Song(R.raw.bondjames, "Bond, James Bond", "Fritol"),
            new Song(R.raw.pivo, "Pivo", "Fritol ft. Barman"),
            new Song(R.raw.ostryzoubky, "Ostrý zoubky", "Fritol ft. Hobitofil"),
            new Song(R.raw.tahnehelium, "Táhne z tebe hélium", "Fritol"),
            new Song(R.raw.tupyjakrit, "Je tupý jak řiť", "Fritol ft. Bimbo")
    };
    public final Song[] hlaskyHobitofil = {
            new Song(R.raw.ostryzoubky, "Ostrý zoubky", "Fritol ft. Hobitofil"),
            new Song(R.raw.zubymammensi, "Zuby mám menší", "Hobitofil"),
            new Song(R.raw.vyhuloveobetihudobnihopr, "Jsou to vyhulové", "Hobitofil"),
            new Song(R.raw.prizraksjime, "Bude z neho přízrak", "Hobitofil"),
            new Song(R.raw.sexyhlas, "Sexy hlas", "Hobitofil"),
            new Song(R.raw.grafity, "Grafiti na zdi", "Hobitofil"),
            new Song(R.raw.kunbyposel, "Kuň by z neho pošel", "Hobitofil"),
            new Song(R.raw.anakin, "Anakine", "Hobitofil"),
            new Song(R.raw.masbytmrtvy, "Máš byt mrtvej", "Hobitofil"),
            new Song(R.raw.koupimcarodeje, "Koupím ti jiného čarodeje", "Hobitofil"),
            new Song(R.raw.jakzijes, "Jak žiješ", "Hobitofil ft. Bobromil"),
            new Song(R.raw.kdybychtesjedli, "Kdybych te sjedli", "Hobitofil ft. Bobromil"),
            new Song(R.raw.sipyzhecu, "Šípy z hecu", "Bobromil ft. Hobitofil"),
            new Song(R.raw.udelalbychtosame, "Udělal bych to samé", "Hobitofil")
    };
    public final Song[] hlaskyRozne = {new Song(R.raw.jedenvladne, "Jeden ringoš velí všeckym", "úvod"),
            new Song(R.raw.varovani, "Varovaní", "Ministerstvo zdravotnictvi"),
            new Song(R.raw.pajzlovebezvousu, "Pajzlové a ješte bez vousu", "Barman"),
            new Song(R.raw.jetiosmnact, "Je ti 18 smrade", "Barman"),
            new Song(R.raw.celnikhobitofil, "Je to celnik, Hobiofil", "Barman"),
            new Song(R.raw.paneeee, "Paneee...?", "Barman"),
            new Song(R.raw.vyhulovedontwory, "Dont worry", "Vyhulové"),
            new Song(R.raw.vetrista, "Vetrista šou", "Vyhulové"),
            new Song(R.raw.chcemjenparitkravo, "Chcem jen pařit krávo", "Vyhulové"),
            new Song(R.raw.heliovebalonky, "Héliové balónky", "Smradupán")
    };
    public final Song[] hlaskySmajdalf = {new Song(R.raw.pisnickasmajdalfa, "Trápna písnička", "Šmajdalf"),
            new Song(R.raw.magicseneflaka, "Magič se nikdy nefláka", "Šmajdalf"),
            new Song(R.raw.smiech, "Hehehuhu", "Fritol ft. Šmajdalf"),
            new Song(R.raw.nakupysmajdalfa, "Nákupy Šmajdalfa", "Fritol ft. Šmajdalf"),
            new Song(R.raw.cervenykolecko, "Červený kolečko na dlani", "Šmajdalf"),
            new Song(R.raw.nesezerto, "Nesežer to", "Šmajdalf"),
            new Song(R.raw.zdrhamnesezerto, "Zdrhám, nsežer to", "Šmajdalf"),
            new Song(R.raw.nesezralsho, "Nesežrals ho?(s BafikiBaf a se Slimem)", "Šmajdalf"),
            new Song(R.raw.smajdalfkouri, "Šmajdalf kouři", "Šmajdalf"),
            new Song(R.raw.skushelium, "Mnel bys skusit hélium", "Šmajdalf ft. Smradupán"),
            new Song(R.raw.smoulalala, "Šmoulalala", "Šmajdalf ft. Smradupán"),
            new Song(R.raw.svetlusky, "Svetlušky", "Šmajdalf"),
            new Song(R.raw.svetluskasupgradem, "Težká válečná svetluška", "Šmajdalf"),
            new Song(R.raw.bezbalounkovsivprdeli, "Bez balounkov si v prdeli", "Šmajdalf"),
            new Song(R.raw.comnedrzi, "Co mne drží ve vzduchu", "Šmajdalf"),
            new Song(R.raw.radilisme, "Řádili sme", "Šmajdalf"),
            new Song(R.raw.toaletakveverky, "Toaleťák a veverky", "Šmajdalf")
    };
    public final Song[] hlaskySpolocesntvaTentonancu = {new Song(R.raw.urvetitoruce, "Urve ti to ruce", "Choboti"),
            new Song(R.raw.nabenzinku, "Do Roklinky, na benzínku", "Hobitofil ft. Choboti"),
            new Song(R.raw.slusimito, "Sluší mi to?", "Legoland"),
            new Song(R.raw.umimvarit, "Ja umím vařit", "Slim"),
            new Song(R.raw.jsemdebil, "Ja jsem debil", "Chobot"),
            new Song(R.raw.trosku, "Tak trošku...", "Chobot"),
            new Song(R.raw.onisivsimli, "Oni si vsimli", "Chobot"),
            new Song(R.raw.spravnedojidelny, "Jdem tady správne do jídelny", "Choboti"),
            new Song(R.raw.fotbal, "Pozvani na fotbal", "Pajzl"),
            new Song(R.raw.ufo, "UFO", "Spoločenstvo tentonancu"),
            new Song(R.raw.veverkysemsti, "Veverky se mstí", "Legoland"),
            new Song(R.raw.jetomagor, "Je to magor", "Slim ft. Hobitofil"),
            new Song(R.raw.geronimooo, "Geronimooo", "Pajzl"),
            new Song(R.raw.ukrastvousy, "Chtejí mi ukrást vousy", "Pajzl"),
            new Song(R.raw.podmediskutovat, "Podme vo tom diskutovat", "Legoland")
    };
    public final Song[] hlaskyVLothoriene = {new Song(R.raw.bohatymberu, "Bohatým beru, na chudé seru", "Babin Hud"),
            new Song(R.raw.nehybapusou, "Kecám a nehýbam pusou", "Galadriel"),
            new Song(R.raw.smrdizebyja, "Neco tady smrdí, žeby ja?", "Celeborn")
    };
    public final Song[] hlaskyVRoklinke = {new Song(R.raw.vodpriskni, "Vodprískni", "Bimbo"),
            new Song(R.raw.starycmoud, "Starý čmoud", "Bimbo"),
            new Song(R.raw.oslava, "Oslava Bimbových narodenín", "Bimbo"),
            new Song(R.raw.bimboodchadza, "Odchod, co je Matrix", "Bimbo"),
            new Song(R.raw.bbbbb, "Bbbbb", "Bbbbb"),
            new Song(R.raw.copakcelnik, "Copak copak, celnik bude grilovat", "Bbbb"),
            new Song(R.raw.domakuchare, "Zavezu ho domu, mame dobré kuchaře", "Bbbb"),
            new Song(R.raw.vareni, "Vareni", "Bbbb"),
            new Song(R.raw.matrixvsude, "Matrix je všude", "Elrond"),
            new Song(R.raw.cojedulezite, "Co je duležité", "Spoločenstvo ft. Elrond"),
            new Song(R.raw.strojeovladaji, "Stroje ovládají mou mysl", "Elrond"),
            new Song(R.raw.usidospicata, "Uši do špicata", "Elrond"),
            new Song(R.raw.budescukrarem, "Budeš cukrářem", "Bbbb"),
            new Song(R.raw.heliumtoneni, "Hélium to rozhodne není", "Elrond"),
            new Song(R.raw.smrdis, "Smrdíš až veverkám pukli hlavy", "Elrond"),
            new Song(R.raw.spolocenstvotentonancu, "Spoločenstvo tentonancu", "Elrond ft. Choboti"),
            new Song(R.raw.yoda, "Kostým z hviezdnych válek", "Bimbo"),
            new Song(R.raw.tupyjakrit, "Je tupý jak řiť", "Fritol ft. Bimbo")
    };

    public ArrayList<Song> hlaskyVsetky = null;

    public void makeList(){
        if (hlaskyVsetky == null){
            hlaskyVsetky = new ArrayList<>();
            hlaskyVsetky.addAll(Arrays.asList(hlaskyBobromil));
            hlaskyVsetky.addAll(Arrays.asList(hlaskyFritol));
            hlaskyVsetky.addAll(Arrays.asList(hlaskyHobitofil));
            hlaskyVsetky.addAll(Arrays.asList(hlaskyRozne));
            hlaskyVsetky.addAll(Arrays.asList(hlaskySmajdalf));
            hlaskyVsetky.addAll(Arrays.asList(hlaskySpolocesntvaTentonancu));
            hlaskyVsetky.addAll(Arrays.asList(hlaskyVLothoriene));
            hlaskyVsetky.addAll(Arrays.asList(hlaskyVRoklinke));
        }
    }




}
