package redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

/**
 * Класс-Enum содержащий перечесление языков
 */

@AllArgsConstructor
public enum Language {
    AUTO("Auto"),
    SQ("Albanian"),
    AR("Arabic"),
    AZ("Azerbaijani"),
    EU("Basque"),
    BS("Bosnian"),
    BG("Bulgarian"),
    CA("Catalan"),
    ZH("Chinese/Simplified"),
    ZH_TW("Chinese/Traditional"),
    HR("Croatian"),
    CS("Czech"),
    DA("Danish"),
    NL("Dutch"),
    EN("English Английский"),
    EN_GB("English/British"),
    ET("Estonian"),
    FI("Finnish"),
    FR("French"),
    GL("Galician"),
    DE("German"),
    EL("Greek"),
    HE("Hebrew"),
    HU("Hungarian"),
    ID("Indonesian"),
    IT("Italian"),
    JA("Japanese"),
    KO("Korean"),
    LV("Latvian"),
    LT("Lithuanian"),
    MK("Macedonian"),
    MN("Mongolian"),
    NO("Norwegian"),
    FA("Persian"),
    PL("Polish"),
    PT("Portuguese"),
    PT_BR("Portuguese/Brazil"),
    RO("Romanian"),
    RU("Russian Русский"),
    SR_YU("Serbian"),
    SR("Serbian Cyrillic"),
    SK("Slovak"),
    SL("Slovene"),
    ES("Spanish"),
    ES_PA("Spanish/Panama"),
    SV("Swedish"),
    TH("Thai"),
    TR("Turkish"),
    UK("Ukrainian"),
    VI("Vietnamese");

    private final String description;

    private String getDescription() {
        return description;
    }

    public static Language of(final String languageAbbreviation) {
        return Stream.of(values())
                .filter(value -> value.name().contains(languageAbbreviation.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Язык не найден по значению: " + languageAbbreviation));
    }

    public static Language getByDescription(final String description) {
        return Stream.of(values())
                .filter(value -> value.getDescription().contains(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Язык не найден по описанию: " + description));
    }
}
