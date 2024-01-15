# LR4 JMS
В качестве броккера был использован RabbitMQ 3.12.2, описан конфигурационный файл [RabbitMQ](https://github.com/badasqi/ESA_JMS/blob/main/src/main/java/com/example/esalab3/configRabbit/RabbitMQConfig.java)

Были добавлены 2 таблицы в БД - ChangeLog (строка - инфа об изменении), Notification (строка - почты кому рассылаем уведомление), условие рассылки.

Реализованы в /messaging Consumer (слушает сообщения из RabbitMQ, сохраняет изменения в таблицу ChangeLog)

MailConsumer(слушает сообщения из RabbitMQ, формирует и отправляет SimpleMailMessage на адреса из таблицы Notification уведомления об удалении объектов из БД), Producer(отправляет сообщения в RabbitMQ)

## Пример сообщения

В логах следущие сообщения
![image](https://github.com/badasqi/ESA_JMS/assets/78803025/ba44b2e1-96ac-459a-a6c9-3f8e95c62e23)

В теле письма переобределённый toString() для ChangeLog.
![image](https://github.com/badasqi/ESA_JMS/assets/78803025/0c91729c-dd18-47a4-bafa-a9e1d9debe9f)



