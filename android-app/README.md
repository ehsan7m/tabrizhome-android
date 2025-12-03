# TabrizHome Android App

اپلیکیشن اندروید برای سایت وردپرسی آگهی املاک `tabrizhome.com` با Jetpack Compose، Retrofit/Moshi و Hilt.

## اجرا
1. در Android Studio 2023+ روی فولدر `android-app` باز کنید.
2. اگر فایل `gradle/wrapper/gradle-wrapper.jar` وجود ندارد، یک‌بار دستور زیر را بزنید تا wrapper کامل شود:
   - ویندوز: `gradlew.bat wrapper --gradle-version 8.6`
   - مک/لینوکس: `./gradlew wrapper --gradle-version 8.6`
3. Build/Run روی شبیه‌ساز یا دستگاه (minSdk 24، target 34).

## ساختار
- `app/build.gradle`: تنظیمات ماژول، Compose، Retrofit، Hilt.
- `app/src/main/java/com/tabrizhome/app/data`: مدل‌ها و ریپازیتوری.
- `app/src/main/java/com/tabrizhome/app/ui`: ناوبری، ViewModelها، اسکرین‌ها و Theme.

## APIها
- لیست: `GET https://www.tabrizhome.com/wp-json/wp/v2/properties?_embed`
- جزئیات: `GET https://www.tabrizhome.com/wp-json/wp/v2/properties/{id}?_embed`
- پارامترهای متداول: `page`, `per_page`, `search` و فیلترهای متا (قیمت، متراژ، اتاق) را می‌توانید بر اساس JSON موجود اضافه کنید.
