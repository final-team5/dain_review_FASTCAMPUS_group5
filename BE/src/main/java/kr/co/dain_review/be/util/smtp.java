package kr.co.dain_review.be.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class smtp {
    public static void emailSend(String email, int RandNum) {
        try {
            final String username = "daingiplan2023@gmail.com";
            final String password = "judcvfoeqguzcqww";

            // SMTP 서버 정보를 설정한다.
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("플레이스 매니저 인증번호");
            String htmlContent = emailForm.replace("{verification_code}", String.valueOf(RandNum));
            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private static String emailForm =
            "        <html\n" +
            "        xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
            "        xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
            "        xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
            "        >\n" +
            "        <head>\n" +
            "            <link\n" +
            "            href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\"\n" +
            "            rel=\"stylesheet\"\n" +
            "            type=\"text/css\"\n" +
            "            />\n" +
            "        </head>\n" +
            "        <body\n" +
            "            class=\"clean-body u_body\"\n" +
            "            style=\"\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            -webkit-text-size-adjust: 100%;\n" +
            "            background-color: #f9f9f9;\n" +
            "            color: #000000;\n" +
            "            \"\n" +
            "        >\n" +
            "            <table\n" +
            "            id=\"u_body\"\n" +
            "            style=\"\n" +
            "                border-collapse: collapse;\n" +
            "                table-layout: fixed;\n" +
            "                border-spacing: 0;\n" +
            "                mso-table-lspace: 0pt;\n" +
            "                mso-table-rspace: 0pt;\n" +
            "                vertical-align: top;\n" +
            "                min-width: 320px;\n" +
            "                margin: 0 auto;\n" +
            "                background-color: #f9f9f9;\n" +
            "                width: 100%;\n" +
            "            \"\n" +
            "            cellpadding=\"0\"\n" +
            "            cellspacing=\"0\"\n" +
            "            >\n" +
            "            <tbody>\n" +
            "                <tr style=\"vertical-align: top\">\n" +
            "                <td\n" +
            "                    style=\"\n" +
            "                    word-break: break-word;\n" +
            "                    border-collapse: collapse !important;\n" +
            "                    vertical-align: top;\n" +
            "                    \"\n" +
            "                >\n" +
            "                    <div\n" +
            "                    class=\"u-row-container\"\n" +
            "                    style=\"padding: 0px; background-color: transparent\"\n" +
            "                    >\n" +
            "                    <div\n" +
            "                        class=\"u-row\"\n" +
            "                        style=\"\n" +
            "                        margin: 0 auto;\n" +
            "                        min-width: 320px;\n" +
            "                        max-width: 600px;\n" +
            "                        overflow-wrap: break-word;\n" +
            "                        word-wrap: break-word;\n" +
            "                        word-break: break-word;\n" +
            "                        background-color: transparent;\n" +
            "                        \"\n" +
            "                    >\n" +
            "                        <div\n" +
            "                        style=\"\n" +
            "                            border-collapse: collapse;\n" +
            "                            display: table;\n" +
            "                            width: 100%;\n" +
            "                            height: 100%;\n" +
            "                            background-color: transparent;\n" +
            "                        \"\n" +
            "                        >\n" +
            "                        <div\n" +
            "                            class=\"u-col u-col-100\"\n" +
            "                            style=\"\n" +
            "                            max-width: 320px;\n" +
            "                            min-width: 600px;\n" +
            "                            display: table-cell;\n" +
            "                            vertical-align: top;\n" +
            "                            \"\n" +
            "                        >\n" +
            "                            <div style=\"height: 100%; width: 100% !important\">\n" +
            "                            <div\n" +
            "                                style=\"\n" +
            "                                box-sizing: border-box;\n" +
            "                                height: 100%;\n" +
            "                                padding: 0px;\n" +
            "                                border-top: 0px solid transparent;\n" +
            "                                border-left: 0px solid transparent;\n" +
            "                                border-right: 0px solid transparent;\n" +
            "                                border-bottom: 0px solid transparent;\n" +
            "                                \"\n" +
            "                            ></div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div\n" +
            "                    class=\"u-row-container\"\n" +
            "                    style=\"padding: 0px; background-color: transparent\"\n" +
            "                    >\n" +
            "                    <div\n" +
            "                        class=\"u-row\"\n" +
            "                        style=\"\n" +
            "                        margin: 0 auto;\n" +
            "                        min-width: 320px;\n" +
            "                        max-width: 600px;\n" +
            "                        overflow-wrap: break-word;\n" +
            "                        word-wrap: break-word;\n" +
            "                        word-break: break-word;\n" +
            "                        background-color: #ffffff;\n" +
            "                        \"\n" +
            "                    >\n" +
            "                        <div\n" +
            "                        style=\"\n" +
            "                            border-collapse: collapse;\n" +
            "                            display: table;\n" +
            "                            width: 100%;\n" +
            "                            height: 100%;\n" +
            "                            background-color: transparent;\n" +
            "                        \"\n" +
            "                        >\n" +
            "                        <div\n" +
            "                            class=\"u-col u-col-100\"\n" +
            "                            style=\"\n" +
            "                            max-width: 320px;\n" +
            "                            min-width: 600px;\n" +
            "                            display: table-cell;\n" +
            "                            vertical-align: top;\n" +
            "                            \"\n" +
            "                        >\n" +
            "                            <div style=\"height: 100%; width: 100% !important\">\n" +
            "                            <div\n" +
            "                                style=\"\n" +
            "                                box-sizing: border-box;\n" +
            "                                height: 100%;\n" +
            "                                padding: 0px;\n" +
            "                                border-top: 0px solid transparent;\n" +
            "                                border-left: 0px solid transparent;\n" +
            "                                border-right: 0px solid transparent;\n" +
            "                                border-bottom: 0px solid transparent;\n" +
            "                                \"\n" +
            "                            >\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 20px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <table\n" +
            "                                        width=\"100%\"\n" +
            "                                        cellpadding=\"0\"\n" +
            "                                        cellspacing=\"0\"\n" +
            "                                        border=\"0\"\n" +
            "                                        >\n" +
            "                                        <tr>\n" +
            "                                            <td\n" +
            "                                            style=\"\n" +
            "                                                padding-right: 0px;\n" +
            "                                                padding-left: 0px;\n" +
            "                                            \"\n" +
            "                                            align=\"center\"\n" +
            "                                            >\n" +
            "                                            </td>\n" +
            "                                        </tr>\n" +
            "                                        </table>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "                            </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div\n" +
            "                    class=\"u-row-container\"\n" +
            "                    style=\"padding: 0px; background-color: transparent\"\n" +
            "                    >\n" +
            "                    <div\n" +
            "                        class=\"u-row\"\n" +
            "                        style=\"\n" +
            "                        margin: 0 auto;\n" +
            "                        min-width: 320px;\n" +
            "                        max-width: 600px;\n" +
            "                        overflow-wrap: break-word;\n" +
            "                        word-wrap: break-word;\n" +
            "                        word-break: break-word;\n" +
            "                        background-color: #17217a;\n" +
            "                        \"\n" +
            "                    >\n" +
            "                        <div\n" +
            "                        style=\"\n" +
            "                            border-collapse: collapse;\n" +
            "                            display: table;\n" +
            "                            width: 100%;\n" +
            "                            height: 100%;\n" +
            "                            background-color: transparent;\n" +
            "                            display: flex;\n" +
            "                            flex-direction: column;\n" +
            "                        \"\n" +
            "                        >\n" +
            "                        <div\n" +
            "                            class=\"u-col u-col-100\"\n" +
            "                            style=\"\n" +
            "                            max-width: 320px;\n" +
            "                            min-width: 600px;\n" +
            "                            display: table-cell;\n" +
            "                            vertical-align: top;\n" +
            "                            \"\n" +
            "                        >\n" +
            "                            <div style=\"height: 100%; width: 100% !important\">\n" +
            "                            <div\n" +
            "                                style=\"\n" +
            "                                box-sizing: border-box;\n" +
            "                                height: 100%;\n" +
            "                                padding: 0px;\n" +
            "                                border-top: 0px solid transparent;\n" +
            "                                border-left: 0px solid transparent;\n" +
            "                                border-right: 0px solid transparent;\n" +
            "                                border-bottom: 0px solid transparent;\n" +
            "                                \"\n" +
            "                            >\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 40px 10px 10px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <table\n" +
            "                                        width=\"100%\"\n" +
            "                                        cellpadding=\"0\"\n" +
            "                                        cellspacing=\"0\"\n" +
            "                                        border=\"0\"\n" +
            "                                        >\n" +
            "                                        <tr>\n" +
            "                                            <td\n" +
            "                                            style=\"\n" +
            "                                                padding-right: 0px;\n" +
            "                                                padding-left: 0px;\n" +
            "                                            \"\n" +
            "                                            align=\"center\"\n" +
            "                                            >\n" +
            "                                            <img\n" +
            "                                                align=\"center\"\n" +
            "                                                border=\"0\"\n" +
            "                                                src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\"\n" +
            "                                                alt=\"Image\"\n" +
            "                                                title=\"Image\"\n" +
            "                                                style=\"\n" +
            "                                                outline: none;\n" +
            "                                                text-decoration: none;\n" +
            "                                                -ms-interpolation-mode: bicubic;\n" +
            "                                                clear: both;\n" +
            "                                                display: inline-block !important;\n" +
            "                                                border: none;\n" +
            "                                                height: auto;\n" +
            "                                                float: none;\n" +
            "                                                width: 26%;\n" +
            "                                                max-width: 150.8px;\n" +
            "                                                \"\n" +
            "                                                width=\"150.8\"\n" +
            "                                            />\n" +
            "                                            </td>\n" +
            "                                        </tr>\n" +
            "                                        </table>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 10px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <div\n" +
            "                                        style=\"\n" +
            "                                            font-size: 14px;\n" +
            "                                            color: #e5eaf5;\n" +
            "                                            line-height: 140%;\n" +
            "                                            text-align: center;\n" +
            "                                            word-wrap: break-word;\n" +
            "                                        \"\n" +
            "                                        >\n" +
            "                                        <p style=\"font-size: 14px; line-height: 140%\">\n" +
            "\n" +
            "                                        </p>\n" +
            "                                        </div>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 0px 10px 31px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <div\n" +
            "                                        style=\"\n" +
            "                                            font-size: 14px;\n" +
            "                                            color: #e5eaf5;\n" +
            "                                            line-height: 140%;\n" +
            "                                            text-align: center;\n" +
            "                                            word-wrap: break-word;\n" +
            "                                        \"\n" +
            "                                        >\n" +
            "                                        <p style=\"font-size: 14px; line-height: 140%\">\n" +
            "                                            <span\n" +
            "                                            style=\"\n" +
            "                                                font-size: 28px;\n" +
            "                                                line-height: 39.2px;\n" +
            "                                            \"\n" +
            "                                            ><strong\n" +
            "                                                ><span\n" +
            "                                                style=\"\n" +
            "                                                    line-height: 39.2px;\n" +
            "                                                    font-size: 28px;\n" +
            "                                                \"\n" +
            "                                                >이메일 인증번호 발급\n" +
            "                                                </span></strong\n" +
            "                                            >\n" +
            "                                            </span>\n" +
            "                                        </p>\n" +
            "                                        </div>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "                            </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div\n" +
            "                    class=\"u-row-container\"\n" +
            "                    style=\"padding: 0px; background-color: transparent\"\n" +
            "                    >\n" +
            "                    <div\n" +
            "                        class=\"u-row\"\n" +
            "                        style=\"\n" +
            "                        margin: 0 auto;\n" +
            "                        min-width: 320px;\n" +
            "                        max-width: 600px;\n" +
            "                        overflow-wrap: break-word;\n" +
            "                        word-wrap: break-word;\n" +
            "                        word-break: break-word;\n" +
            "                        background-color: #ffffff;\n" +
            "                        \"\n" +
            "                    >\n" +
            "                        <div\n" +
            "                        style=\"\n" +
            "                            border-collapse: collapse;\n" +
            "                            display: table;\n" +
            "                            width: 100%;\n" +
            "                            height: 100%;\n" +
            "                            background-color: transparent;\n" +
            "                        \"\n" +
            "                        >\n" +
            "                        <div\n" +
            "                            class=\"u-col u-col-100\"\n" +
            "                            style=\"\n" +
            "                            max-width: 320px;\n" +
            "                            min-width: 600px;\n" +
            "                            display: table-cell;\n" +
            "                            vertical-align: top;\n" +
            "                            \"\n" +
            "                        >\n" +
            "                            <div style=\"height: 100%; width: 100% !important\">\n" +
            "                            <div\n" +
            "                                style=\"\n" +
            "                                box-sizing: border-box;\n" +
            "                                height: 100%;\n" +
            "                                padding: 0px;\n" +
            "                                border-top: 0px solid transparent;\n" +
            "                                border-left: 0px solid transparent;\n" +
            "                                border-right: 0px solid transparent;\n" +
            "                                border-bottom: 0px solid transparent;\n" +
            "                                \"\n" +
            "                            >\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 33px 55px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <div\n" +
            "                                        style=\"\n" +
            "                                            font-size: 14px;\n" +
            "                                            line-height: 160%;\n" +
            "                                            text-align: center;\n" +
            "                                            word-wrap: break-word;\n" +
            "                                        \"\n" +
            "                                        >\n" +
            "                                        <p style=\"font-size: 14px; line-height: 160%\">\n" +
            "                                            <span\n" +
            "                                            style=\"\n" +
            "                                                font-size: 22px;\n" +
            "                                                line-height: 35.2px;\n" +
            "                                            \"\n" +
            "                                            >안녕하세요,\n" +
            "                                            </span>\n" +
            "                                        </p>\n" +
            "                                        <p style=\"font-size: 14px; line-height: 160%\">\n" +
            "                                            <span\n" +
            "                                            style=\"\n" +
            "                                                font-size: 18px;\n" +
            "                                                line-height: 28.8px;\n" +
            "                                            \"\n" +
            "                                            >인증번호 안내 메일입니다.\n" +
            "                                            </span>\n" +
            "                                        </p>\n" +
            "                                        </div>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 10px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <div align=\"center\">\n" +
            "                                        <div\n" +
            "                                            href=\"\"\n" +
            "                                            target=\"_blank\"\n" +
            "                                            class=\"v-button\"\n" +
            "                                            style=\"\n" +
            "                                            box-sizing: border-box;\n" +
            "                                            display: inline-block;\n" +
            "                                            font-family: 'Cabin', sans-serif;\n" +
            "                                            text-decoration: none;\n" +
            "                                            -webkit-text-size-adjust: none;\n" +
            "                                            text-align: center;\n" +
            "                                            color: #ffffff;\n" +
            "                                            background-color: #ff6600;\n" +
            "                                            border-radius: 4px;\n" +
            "                                            -webkit-border-radius: 4px;\n" +
            "                                            -moz-border-radius: 4px;\n" +
            "                                            width: auto;\n" +
            "                                            max-width: 100%;\n" +
            "                                            overflow-wrap: break-word;\n" +
            "                                            word-break: break-word;\n" +
            "                                            word-wrap: break-word;\n" +
            "                                            mso-border-alt: none;\n" +
            "                                            font-size: 14px;\n" +
            "                                            \"\n" +
            "                                        >\n" +
            "                                            <span\n" +
            "                                            style=\"\n" +
            "                                                display: block;\n" +
            "                                                padding: 14px 44px 13px;\n" +
            "                                                line-height: 120%;\n" +
            "                                            \"\n" +
            "                                            ><span style=\"line-height: 16.8px\"\n" +
            "                                                ><strong\n" +
            "                                                ><span style=\"line-height: 16.8px\"\n" +
            "                                                    >{verification_code}</span\n" +
            "                                                ></strong\n" +
            "                                                >\n" +
            "                                            </span>\n" +
            "                                            </span>\n" +
            "                                        </div>\n" +
            "                                        <!--[if mso]></center></v:roundrect><![endif]-->\n" +
            "                                        </div>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 33px 55px 60px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <div\n" +
            "                                        style=\"\n" +
            "                                            font-size: 14px;\n" +
            "                                            line-height: 160%;\n" +
            "                                            text-align: center;\n" +
            "                                            word-wrap: break-word;\n" +
            "                                        \"\n" +
            "                                        >\n" +
            "\n" +
            "                                        </div>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "                            </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div\n" +
            "                    class=\"u-row-container\"\n" +
            "                    style=\"padding: 0px; background-color: transparent\"\n" +
            "                    >\n" +
            "                    <div\n" +
            "                        class=\"u-row\"\n" +
            "                        style=\"\n" +
            "                        margin: 0 auto;\n" +
            "                        min-width: 320px;\n" +
            "                        max-width: 600px;\n" +
            "                        overflow-wrap: break-word;\n" +
            "                        word-wrap: break-word;\n" +
            "                        word-break: break-word;\n" +
            "                        background-color: #e5eaf5;\n" +
            "                        \"\n" +
            "                    >\n" +
            "                        <div\n" +
            "                        style=\"\n" +
            "                            border-collapse: collapse;\n" +
            "                            display: table;\n" +
            "                            width: 100%;\n" +
            "                            height: 100%;\n" +
            "                            background-color: transparent;\n" +
            "                        \"\n" +
            "                        >\n" +
            "                        <div\n" +
            "                            class=\"u-col u-col-100\"\n" +
            "                            style=\"\n" +
            "                            max-width: 320px;\n" +
            "                            min-width: 600px;\n" +
            "                            display: table-cell;\n" +
            "                            vertical-align: top;\n" +
            "                            \"\n" +
            "                        >\n" +
            "                            <div style=\"height: 100%; width: 100% !important\">\n" +
            "                            <div\n" +
            "                                style=\"\n" +
            "                                box-sizing: border-box;\n" +
            "                                height: 100%;\n" +
            "                                padding: 0px;\n" +
            "                                border-top: 0px solid transparent;\n" +
            "                                border-left: 0px solid transparent;\n" +
            "                                border-right: 0px solid transparent;\n" +
            "                                border-bottom: 0px solid transparent;\n" +
            "                                \"\n" +
            "                            >\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                ></table>\n" +
            "                            </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div\n" +
            "                    class=\"u-row-container\"\n" +
            "                    style=\"padding: 0px; background-color: transparent\"\n" +
            "                    >\n" +
            "                    <div\n" +
            "                        class=\"u-row\"\n" +
            "                        style=\"\n" +
            "                        margin: 0 auto;\n" +
            "                        min-width: 320px;\n" +
            "                        max-width: 600px;\n" +
            "                        overflow-wrap: break-word;\n" +
            "                        word-wrap: break-word;\n" +
            "                        word-break: break-word;\n" +
            "                        background-color: #17217a;\n" +
            "                        \"\n" +
            "                    >\n" +
            "                        <div\n" +
            "                        style=\"\n" +
            "                            border-collapse: collapse;\n" +
            "                            display: table;\n" +
            "                            width: 100%;\n" +
            "                            height: 100%;\n" +
            "                            background-color: transparent;\n" +
            "                        \"\n" +
            "                        >\n" +
            "                        <div\n" +
            "                            class=\"u-col u-col-100\"\n" +
            "                            style=\"\n" +
            "                            max-width: 320px;\n" +
            "                            min-width: 600px;\n" +
            "                            display: table-cell;\n" +
            "                            vertical-align: top;\n" +
            "                            \"\n" +
            "                        >\n" +
            "                            <div style=\"height: 100%; width: 100% !important\">\n" +
            "                            <div\n" +
            "                                style=\"\n" +
            "                                box-sizing: border-box;\n" +
            "                                height: 100%;\n" +
            "                                padding: 0px;\n" +
            "                                border-top: 0px solid transparent;\n" +
            "                                border-left: 0px solid transparent;\n" +
            "                                border-right: 0px solid transparent;\n" +
            "                                border-bottom: 0px solid transparent;\n" +
            "                                \"\n" +
            "                            >\n" +
            "                                <table\n" +
            "                                style=\"font-family: 'Cabin', sans-serif\"\n" +
            "                                role=\"presentation\"\n" +
            "                                cellpadding=\"0\"\n" +
            "                                cellspacing=\"0\"\n" +
            "                                width=\"100%\"\n" +
            "                                border=\"0\"\n" +
            "                                >\n" +
            "                                <tbody>\n" +
            "                                    <tr>\n" +
            "                                    <td\n" +
            "                                        style=\"\n" +
            "                                        overflow-wrap: break-word;\n" +
            "                                        word-break: break-word;\n" +
            "                                        padding: 10px;\n" +
            "                                        font-family: 'Cabin', sans-serif;\n" +
            "                                        \"\n" +
            "                                        align=\"left\"\n" +
            "                                    >\n" +
            "                                        <div\n" +
            "                                        style=\"\n" +
            "                                            font-size: 14px;\n" +
            "                                            color: #fafafa;\n" +
            "                                            line-height: 180%;\n" +
            "                                            text-align: center;\n" +
            "                                            word-wrap: break-word;\n" +
            "                                        \"\n" +
            "                                        >\n" +
            "                                        <p style=\"font-size: 14px; line-height: 180%\">\n" +
            "                                            <img src=\"https://www.placemanager.kr/assets/images/white_logo.png\" alt=\"main logo\"  style=\"\n" +
            "                                          max-width: 100px;\n" +
            "\t\t\t\t\t\t\t\t\t\t  object-fit: cover;\n" +
            "                                        \" />\n" +
            "                                        </p>\n" +
            "                                        </div>\n" +
            "                                    </td>\n" +
            "                                    </tr>\n" +
            "                                </tbody>\n" +
            "                                </table>\n" +
            "                            </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "                </td>\n" +
            "                </tr>\n" +
            "            </tbody>\n" +
            "            </table>\n" +
            "        </body>\n" +
            "        </html>";
}
