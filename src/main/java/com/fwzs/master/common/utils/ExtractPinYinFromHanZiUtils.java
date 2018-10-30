package com.fwzs.master.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tyler Yin
 * @create 2017-11-13 10:37
 * @description 从汉字中抽取拼音简码
 **/
public class ExtractPinYinFromHanZiUtils {

    /**
     * 获取每个汉字的拼音首字母，英文字符不变。默认会过滤掉非字母字符
     *
     * @param chinese
     * @return 汉语拼音首字母
     */
    public static String extractPinYinFirstSpell(final String chinese) {
        return extractPinYinFirstSpell(chinese, true);
    }

    /**
     * 获取每个汉字的拼音首字母，英文字符不变
     *
     * @param chinese                  汉字串
     * @param isFilterInvalidCharacter 是否过滤掉非字母字符
     * @return 汉语拼音首字母
     */
    public static String extractPinYinFirstSpell(final String chinese, final boolean isFilterInvalidCharacter) {
        if (StringUtils.isNotBlank(chinese)) {
            final StringBuffer buffer = new StringBuffer();
            final char[] arr = chinese.toCharArray();
            final HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                        if (_t != null) {
                            buffer.append(_t[0].charAt(0));
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    buffer.append(arr[i]);
                }
            }
            final String pinYinJianMa = buffer.toString().replaceAll("\\W", "").trim().toUpperCase();
            return isFilterInvalidCharacter ? filterInvalidCharacter(pinYinJianMa) : pinYinJianMa;
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取汉字串拼音，英文字符不变。默认会过滤掉非字母字符
     *
     * @param chinese
     * @return
     */
    public static String extractPinYinSpell(final String chinese) {
        return extractPinYinSpell(chinese, true);
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese                  汉字串
     * @param isFilterInvalidCharacter 过滤掉非字母字符
     * @return 汉语拼音
     */
    public static String extractPinYinSpell(final String chinese, final boolean isFilterInvalidCharacter) {
        if (StringUtils.isNotBlank(chinese)) {
            final StringBuffer pybf = new StringBuffer();
            final char[] arr = chinese.toCharArray();
            final HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
            return isFilterInvalidCharacter ? filterInvalidCharacter(pybf.toString()) : pybf.toString();
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 过虑掉拼音字符串中的非字母字符
     *
     * @param pinYin
     * @return
     */
    private static String filterInvalidCharacter(final String pinYin) {
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(pinYin);
        while (matcher.find()) {
            return matcher.group();
        }
        return pinYin;
    }
}
