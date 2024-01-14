# LR4 JMS
В качестве броккера был использован RabbitMQ 3.12.2, описан конфигурационный файл [RabbitMQ](https://github.com/badasqi/ESA_JMS/blob/main/src/main/java/com/example/esalab3/configRabbit/RabbitMQConfig.java)

Были добавлены 2 таблицы в БД - ChangeLog (строка - инфа об изменении), Notification (строка - почты кому рассылаем уведомление)

Реализованы в /messaging Consumer (слушает сообщения из RabbitMQ, сохраняет изменения в таблицу ChangeLog)

MailConsumer(слушает сообщения из RabbitMQ, вызывает метод сервиса и отправляет SimpleMailMessage на адреса из таблицы Notification), Producer(отправляет сообщения в RabbitMQ)
