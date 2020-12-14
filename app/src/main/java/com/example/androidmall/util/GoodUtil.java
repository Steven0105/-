package com.example.androidmall.util;

import com.example.androidmall.R;
import com.example.androidmall.bean.Good;

import java.util.Random;

public class GoodUtil {
    //初始化商品
    public static void initGood() {
        if (Global.myDB.goodDao().findAll().size() > 0) {
            return;
        }
        Good[] goods = new Good[30];
        goods[0] = new Good();
        goods[0].title = "零基础学Python（全彩版）Python3.8 全新升级";
        goods[0].price = 69.80f;
        goods[0].description = "1.前一版中文版重印30余次，销量750 000册；\n" +
                "2.针对Python 3.x新特性升级，重写项目代码；\n" +
                "3.真正零基础，自学也轻松；\n" +
                "4.赠送配套学习视频和配套编程环境，边看边学更便捷；\n" +
                "5.赠送Python学习速查地图，掌握编程语言关键步骤，学习不迷路；\n" +
                "6.提供读者学习交流群，不懂就问，近距离接触行业大佬；\n" +
                "7.赠送学习源代码文件、练习答案，助读者零压力掌握Python语言！\n" +
                "\n" +
                "本书内容分为“基础知识”和“项目”两部分。读完本书，读者不仅能快速掌握编程基础知识，还能编写出解决实际问题的代码并开发复杂的项目。第2版沿袭第1版讲解清晰透彻、循序渐进的特点，并全面升级。\n" +
                "\n" +
                "一部分“基础知识”新增Sublime Text、f字符串、大数表示法和常量表示法等主题，并且更准确地描述了Python语言的细节。第二部分“项目”采用更简明的结构、更清晰的语法以及更流行的库和工具，如Plotly和新版本的Django。";
        goods[0].image = R.drawable.good1;

        goods[1] = new Good();
        goods[1].title = "编程导论——以Python为舟";
        goods[1].price = 99.80f;
        goods[1].description = "本书以大量的编程实例与作者多年编程实践的体会来揭示编程的本质，系统性地指导读者如何编程。书中所有代码都用Python语言编写，通过编程实例讲解Python语言的所有知识点，使读者在掌握编程思维和技巧（逻辑思维能力、计划构建能力、循环计算能力、递归求解能力等）的同时，自然而然地熟练掌握Python语言。 本书既适合作为“程序设计基础”“编程导论”“Python语言程序设计”等课程的教材，也适合参加编程竞赛的、自学Python编程的中学生、大中专学生、程序员及普通读者参考。";
        goods[1].image = R.drawable.good2;


        goods[2] = new Good();
        goods[2].title = "可编程序控制器及其应用（三菱 第三版）习题册";
        goods[2].price = 5.00f;
        goods[2].description = "课题一 可编程序控制器基础知识\n" +
                "　任务1 初识可编程序控制器\n" +
                "　任务2 可编程序控制器硬件安装及接线\n" +
                "　任务3 编程软件的安装及使用\n" +
                "课题二 基本控制指令应用\n" +
                "　任务1 河沙自动装载装置控制系统设计与装调\n" +
                "　任务2 卷扬机控制系统设计与装调\n" +
                "　任务3 三相交流异步电动机Y— 降压启动控制系统设计与装调\n" +
                "　任务4 抢答器控制系统设计与装调\n" +
                "　任务5 花式喷泉控制系统设计与装调\n" +
                "课题三 顺序控制设计法及顺序控制指令应用\n" +
                "　任务1 送料小车三地自动往返循环控制系统设计与装调\n" +
                "　任务2 液体自动混合装置控制系统设计与装调\n" +
                "　任务3 自动门控制系统设计与装调";
        goods[2].image = R.drawable.good3;

        goods[3] = new Good();
        goods[3].title = "趣味编程挑战：从Python入门到AI应用";
        goods[3].price = 66.10f;
        goods[3].description = "本书特色\n" +
                "（1）基于 Python新版本，适合零基础的初学者。\n" +
                "（2）采用单元形式编排内容，由浅入深、循序渐进，通过趣味案例激发学生的学习兴趣。\n" +
                "（3）以解决问题为导向，注重计算思维的培养，突出编程能力的重要性。同时，讲解编程知识以“够用”为原则，带领初学者避开技术陷阱。\n" +
                "（4）教学案例丰富，涉及数学计算、绘画、游戏设计和人工智能应用等，让学生体验编程的乐趣。\n" +
                "（5）精心设计课后练习题，让初学者巩固所学知识。";
        goods[3].image = R.drawable.good4;

        goods[4] = new Good();
        goods[4].title = "SAS编程技术教程（第2版）（高等学校计算机基础教育教材精选）";
        goods[4].price = 56f;
        goods[4].description = "作者在前一版的基础上，结合在清华大学的本科生与研究生教学实践，修改完善而成此书，较之前书有大量创新。现将主要特色简述如下：\n" +
                "\n" +
                "1.编程技术与实际问题相结合。书中配备了大量有实际意义的例子，加上多年来积累的练习题、水平测试题和综合练习题，能够帮助读者轻松地掌握SAS编程技术，克服了许多编程专著只是空洞地解释语句、创建没有实际意义例程的弊端。\n" +
                "\n" +
                "2.该书重点突出SAS语句的应用功能，旨在发挥SAS系统的优势，使读者充分体会到SAS系统实现复杂数据处理的强大功能。\n" +
                "\n" +
                "3.语言简练、准确。一般情况下，一句话完成对SAS语句、过程相关选项的解释。\n" +
                "\n" +
                "4.专业金融数据网站的在线技术支持。该书得到了专业金融数据网站（www.resset.cn）的在线技术支持，提供配套数据集、程序下载与疑难问题解答等服务，在很大程度上方便了读者的学习和应用。";
        goods[4].image = R.drawable.good5;


        goods[5] = new Good();
        goods[5].title = "SAS编程技术教程（第3版）（高等学校计算机基础教育教材精选）";
        goods[5].price = 56f;
        goods[5].description = "作者在前一版的基础上，结合在清华大学的本科生与研究生教学实践，修改完善而成此书，较之前书有大量创新。现将主要特色简述如下：\n" +
                "\n" +
                "1.编程技术与实际问题相结合。书中配备了大量有实际意义的例子，加上多年来积累的练习题、水平测试题和综合练习题，能够帮助读者轻松地掌握SAS编程技术，克服了许多编程专著只是空洞地解释语句、创建没有实际意义例程的弊端。\n" +
                "\n" +
                "2.该书重点突出SAS语句的应用功能，旨在发挥SAS系统的优势，使读者充分体会到SAS系统实现复杂数据处理的强大功能。\n" +
                "\n" +
                "3.语言简练、准确。一般情况下，一句话完成对SAS语句、过程相关选项的解释。\n" +
                "\n" +
                "4.专业金融数据网站的在线技术支持。该书得到了专业金融数据网站（www.resset.cn）的在线技术支持，提供配套数据集、程序下载与疑难问题解答等服务，在很大程度上方便了读者的学习和应用。";
        goods[5].image = R.drawable.good6;

        goods[6] = new Good();
        goods[6].title = "可编程控制技术与应用(高等)\\刘志刚";
        goods[6].price = 35f;
        goods[6].description = "作者在前一版的基础上，结合在清华大学的本科生与研究生教学实践，修改完善而成此书，较之前书有大量创新。现将主要特色简述如下：\n" +
                "\n" +
                "1.编程技术与实际问题相结合。书中配备了大量有实际意义的例子，加上多年来积累的练习题、水平测试题和综合练习题，能够帮助读者轻松地掌握SAS编程技术，克服了许多编程专著只是空洞地解释语句、创建没有实际意义例程的弊端。\n" +
                "\n" +
                "2.该书重点突出SAS语句的应用功能，旨在发挥SAS系统的优势，使读者充分体会到SAS系统实现复杂数据处理的强大功能。\n" +
                "\n" +
                "3.语言简练、准确。一般情况下，一句话完成对SAS语句、过程相关选项的解释。\n" +
                "\n" +
                "4.专业金融数据网站的在线技术支持。该书得到了专业金融数据网站（www.resset.cn）的在线技术支持，提供配套数据集、程序下载与疑难问题解答等服务，在很大程度上方便了读者的学习和应用。";
        goods[6].image = R.drawable.good7;


        goods[7] = new Good();
        goods[7].title = "Android 编程基础";
        goods[7].price = 25f;
        goods[7].description = "第1章　Android开发基础\n" +
                "　1.1 Android概述\n" +
                "　1.2 Android操作系统\n" +
                "　1.3 Android开发环境\n" +
                "　1.4 第一个Android应用程序\n" +
                "第2章　Android Ul基础\n" +
                "　2.1　Android平台Ul架构\n" +
                "　2.2 U1组件的定义和引用\n" +
                "　2.3 U1基础控件\n" +
                "　2.4 U1高级控件\n" +
                "　2.5 U1布局\n" +
                "　2.6 Android事件监听器和事件处理机制\n" +
                "　本章小结\n" +
                "第3章　Android图形处理";
        goods[7].image = R.drawable.good8;

        goods[8] = new Good();
        goods[8].title = "Android编程权威指南 第3版";
        goods[8].price = 25f;
        goods[8].description = "1版和第2版）。Bill一直坚持撰写博客、阅读各类文学作品、作曲奏乐以及著书。 Chris Stewart Big Nerd Ranch的Android团队主管、Android训练营资深讲师。他致力于不断取得进步和精进技能。工作之余，Chris喜欢远足和旅行。 Kristin Marsicano Big Nerd Ranch资深讲师、Android开发者。她喜欢分享知识，对学习、软件开发以及二者的交集充满热情。在授课和开发应用之余，Kristin会为家人烹调美食、练习瑜伽或学习新知识。 王明发 毕业于华东理工大学。软件开发及项目管理者，拥有近十年的软件开发及项目管理经验；除了翻译本书前两版之外，另译有《写给大家看的项目管理书（第3版）》。";
        goods[8].image = R.drawable.good9;
        for (int i = 0; i < 9; i++) {
            goods[i].uuid = i + "";
            goods[i].time = System.currentTimeMillis() + 2000;
        }
        for (int i = 9; i < goods.length; i++) {
            goods[i] = new Good();
            goods[i].time = System.currentTimeMillis();
            goods[i].title = "测试商品" + i;
            goods[i].description = "商品描述：\r\n暂无描述";
            goods[i].price = new Random().nextInt(100);
            goods[i].uuid = i + "";
            goods[i].image = R.drawable.launcher;
        }
        Global.myDB.goodDao().insert(goods);
    }
}
