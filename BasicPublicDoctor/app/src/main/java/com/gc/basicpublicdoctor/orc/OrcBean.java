package com.gc.basicpublicdoctor.orc;

public class OrcBean {
    //IDCardResult front{direction=0, wordsResultNumber=6, address=江苏省溧阳市上兴镇祠堂村委孟家庄村5号, idNumber=320481198811234814, birthday=19881123, name=施小东, gender=男, ethnic=汉}

    private WordsResult words_result;

    public WordsResult getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResult words_result) {
        this.words_result = words_result;
    }

    public static class WordsResult{
        private InfoData 住址;
        private InfoData 出生;
        private InfoData 姓名;
        private InfoData 公民身份号码;
        private InfoData 性别;
        private InfoData 民族;

        public InfoData get住址() {
            return 住址;
        }

        public void set住址(InfoData 住址) {
            this.住址 = 住址;
        }

        public InfoData get出生() {
            return 出生;
        }

        public void set出生(InfoData 出生) {
            this.出生 = 出生;
        }

        public InfoData get姓名() {
            return 姓名;
        }

        public void set姓名(InfoData 姓名) {
            this.姓名 = 姓名;
        }

        public InfoData get公民身份号码() {
            return 公民身份号码;
        }

        public void set公民身份号码(InfoData 公民身份号码) {
            this.公民身份号码 = 公民身份号码;
        }

        public InfoData get性别() {
            return 性别;
        }

        public void set性别(InfoData 性别) {
            this.性别 = 性别;
        }

        public InfoData get民族() {
            return 民族;
        }

        public void set民族(InfoData 民族) {
            this.民族 = 民族;
        }
    }

    public static class InfoData{
        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
