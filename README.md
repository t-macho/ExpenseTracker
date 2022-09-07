# ExpenseTracker
Program je určen ke snadnému sledování příjmů a výdajů na několika účtech. Jednotlivé transakce je možno libovolně přidávat, upravovat a odebírat.
Také je možné přidávat trvalé platby (příjmy či výdaje), které se automaticky při dalším načtení přidají.

### Účty
Po spuštění se zobrazí okno s přehledem existujících účtů a tlačítka na přidání nového účtu, nebo pokračování bez účtu.
Při vytváření nového účtu je třeba vyplnit jméno a heslo, pomocí hesla se pak lze k účtu opětovně přihlásit.
K účtu se pak přiřazují Jednotlivé transakce a opakované platby, obojí se automaticky načte po přihlášení.

### Grafické rozhraní
Hlavní okno se skládá ze tří částí - tabulek, ovládacích prvků a přehledu.

#### Tabulky
V hlavní tabulce se objevují přidané transakce. Tabulka má tři sloupce - název, částku a datum.
Je možné transakce setřídit podle libovolného sloupce kliknutím na něj.
Transakce jsou barevně rozlišeny podle toho, jestli se jedná o příjem (zelená), nebo výdaj (červená).
Dvojklikem na transakci se otevře nové okno, ve kterém je možné transakci upravit. Je zde také k nahlédnutí poznámka.
Transakci je v tomto okně také možné odstranit.
Druhá tabulka se zobrazí/skryje po stisknutí tlačítka `Opakované platby`.
V této tabulce se zobrazují přidané opakované platby (opět barevně rozlišené), které je možno přidávat tlačítkem pod tabulkou
a upravovat opět dvojklikem na příslušnou platbu.

#### Ovládací prvky
Napravo nahoře od tabulky se nachází pětice tlačítek a rozbalovací seznam.
Tlačítka postupně umožňují transakce přidávat, ukládat, načítat, zobrazit/skrýt tabulku opakovaných plateb a měnit účty.
Rozbalovací seznam umožňuje dle zvolené položky filtrovat jen některé transakce (vše, jen příjmy, jen výdaje).
Po kliknutí na tlačítko `Přidat transakci` se otevře nové okno, ve kterém uživatel vyplní detaily transakce,
přičemž název, částka a datum jsou povinné.

#### Přehled
Poslední část hlavního okna je přehled "stavu účtu". Nachází se zde součet příjmů, výdajů a celkový zůstatek současného účtu.

### Ukládání/načítání
Po stisku tlačítka `Uložit` dojde k jedné za dvou věcí:
  - pokud je přihlášen nějaký účet, data se automaticky uloží na správné místo
  - pokud uživatel zvolil možnost `Pokračovat bez účtu`, po stisku tlačítka se objeví okno, kde lze vybrat cílový soubor.
Stiskem tlačítka `Načíst` funguje obdobně, jen místo ukládání načítá.

### Poznámka
V repu je přiložen vzorový soubor `accounts.txt` s jedním uživatelem - jméno je `Tomáš Macho`, heslo `123456`. K tomuto účtu jsou přidané
vzorové transakce a opakované platby.
