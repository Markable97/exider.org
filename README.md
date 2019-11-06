# exider.org
Developing a football app for Amateur leagues

Мобильное клиент серверное приложение через сокеты, предоставляющее информацию с сайта любительской футбольной лиги http://vostok.lfl.ru/

ОПИСАНИЕ:
В приложение на главном экране отображаются вкладки с рассписанием игр ,результатами и турнирной таблицы для выбранного дивизиона из бокового меню. 
По табу на матч в вкладке "Результаты" отображаетсдетальная информация о матче (кто забил, кто ассестировал, кто получал предупреждения).
По табу на команду в таблице - происходит на страницу команды -в которой отображаются состав со статистикой и все матчи команды.
Также предусмтрены несколько режимов использования приложением. Для обычных клиентов не предусмотренны дополнительные возможности 
(Предоставлены функционал описанный выше).
Пользователь судья - имеет возможность выбор матча и заполнения протокола (кто играл, действия игроков)
Пользователь администратор имеет возможность составлять рассписание для выбрраного матча можно выбрать свободное время (Наподобии выбора сеанса в кино)
Добавление ролей происходит в бд и при каждом входе проверяется какой тип пользователь

Все данные берутся из бд. Бд реализована в репозитории https://github.com/Markable97/Database . Приложение общается с сервером через сокеты
. Сервер реализован в репозитории https://github.com/Markable97/server.

ЧТО ИСПОЛЬЗУЮ:
- Применение Navigation Drawer для бокового меню по дивизионам
- Реализация вкладок через tabLayout c динамическим изменением
- Использование RecyclerView.Adapte с Holder
- Использование кастомных диалоговых окон (Например: выбор времени для матча и тип гола)
- Общение с сервером через сокеты
- Прием картинок с сервера в base64 и декодирование для ImageView
- Испольование векторной графики
- Для загрузки использую AsyncTask с диалоговым окном загрузки
- Для сохранения пароля, логина и типа пользоваеля использую SharedPreferences
