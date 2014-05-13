Interkassa_android
==================

Імплементація API Interkassa для ОС Android.

Дана бібліотека надає два рівні інтеґрації платіжної сисетма Interkassa в ваш застосунок.

1. Все, що потрібно зробити – додати в layout, де ви хочете відображати поле для вибору доступних платіжних систем. 
Передати в Interkassa валюту, яку ви хочете прийняти і суму, наприклад: <br/>
<i>Interkassa.setCurrentCurrency(Money.Ruble);<br/>
Interkassa.setCurrentAmount(200.d); </i><br/>
Додати в ваш Manifest файл рядки<br/>
<i>
<activity<br/>
    android:name="com.interkassa.helpers.WebViewActivity_"<br/>
    android:label="@string/app_name" ><br/>
</activity><br/>
</i>
Все! Ви готові приймати платежі в вашу касу в Interkassa.<br/>