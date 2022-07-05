# Социальная сеть sNet
## Задача: добавление функционала для работы с заметками
### Поля класса Note, не использованные в проекте:
* ownerId
* viewUrl
### Поля класса Comment, не использованные в проекте:
* replyTo
* guid
### Обработка исключений:
* При вызове функции для отсутствующего объекта выбрасывается исключение
* При удалении заметки удаляются все комментарии к ней
* При попытке отредактировать удаленный комментарий или заметку выбрасывается исключение
* При попытке удалить уже удаленный объект ничего не происходит - объект остается удаленным, функция возвращает 1
* При попытке восстановить неудаленный объект ничего не происходит - объект остается неудаленным, функция возвращает 1

## Задача: добавление функционала для работы с чатами
* Добавлен класс user
* Добавлен севисный класс ChatService
* Добавлены чаты между двумя пользователями
* Добавлены сообщения в чатах
### Детали реализации:
* Чаты вложены в ChatService списком
* Поиск чата выполнен по пользователям (при неуспешном поиске выбрасывается исключение)
* Сообщения вложены в чаты списком
* Поиск сообщений внутри чата выполнен по ID (при неуспешном поиске выбрасывается исключение)
* При отправке сообщения пользователю, с которым еще нет чата, создается чат
* При удалении последнего сообщения в чате чат удаляется
* Непрочитанными могут быть только входящие сообщения
* Является сообщение входящим или исходящим, определяется совпадением ID вызывающего пользователя и значением senderID

### Задача 10: ChatService Optimization
* Код функций выполнен в виде цепочек вызовов
* Структуры хранения данных заменены на MutableMap с поиском по ключу