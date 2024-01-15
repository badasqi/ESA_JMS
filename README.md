# LR4 JMS
В качестве броккера был использован RabbitMQ 3.12.2, описан конфигурационный файл [RabbitMQ](https://github.com/badasqi/ESA_JMS/blob/main/src/main/java/com/example/esalab3/configRabbit/RabbitMQConfig.java)

Были добавлены 2 таблицы в БД - ChangeLog (строка - инфа об изменении), Notification (строка - почты кому рассылаем уведомление)

Реализованы в /messaging Consumer (слушает сообщения из RabbitMQ, сохраняет изменения в таблицу ChangeLog)

MailConsumer(слушает сообщения из RabbitMQ, формирует и отправляет SimpleMailMessage на адреса из таблицы Notification уведомления об удалении объектов из БД), Producer(отправляет сообщения в RabbitMQ)

## Пример сообщения
В теле письма переобределённый toString() для ChangeLog.
![image](https://github.com/badasqi/ESA_JMS/assets/78803025/c297be18-98b1-42bc-84eb-da39d36af4b5)


