# Saga Pattern Projesi

Bu proje, mikroservis mimarisinde Saga Pattern'in uygulanmasını gösteren bir örnektir. Saga Pattern, dağıtık sistemlerde uzun süreli işlemleri yönetmek için kullanılan bir desen olup, her bir işlem adımı farklı bir mikroservis tarafından gerçekleştirilir. 

## Kullanılan Teknolojiler

- **Java 17**: Projenin ana programlama dili.
- **Spring Boot**: Mikroservislerin geliştirilmesi ve yönetimi için kullanılmıştır.
- **Spring Cloud**: Dağıtık sistemlerde servis keşfi ve yük dengeleme gibi özellikleri sağlamak için kullanılmıştır.
- **Spring Data JPA**: Veritabanı işlemlerinin yönetimi için kullanılmıştır.
- **PostgreSQL**: Projede kullanılan ilişkisel veritabanı yönetim sistemi.
- **RabbitMQ**: Saga Pattern'in asenkron adımlarını yönetmek için kullanılan mesajlaşma sistemi.
- **Docker**: Mikroservisleri konteynerleştirip çalıştırmak için kullanılmıştır.
- **Maven**: Proje bağımlılıkları ve yapılandırma yönetimi için kullanılmıştır.

## Mikroservisler

Proje, aşağıdaki mikroservisleri içermektedir:

- **Sipariş Servisi (Order Service)**: Sipariş oluşturma ve yönetim işlemlerini yürütür.
- **Envanter Servisi (Inventory Service)**: Ürünlerin stok kontrolünü yapar.
- **Ödeme Servisi (Payment Service)**: Ödeme süreçlerini yönetir.

Her bir servis, kendi bağımsız veritabanına sahip olup, mikroservisler arasındaki iletişim Kafka üzerinden sağlanmaktadır.

## Veritabanı Yapısı

Projede **PostgreSQL** veritabanı kullanılmıştır. Her mikroservisin kendi veritabanı vardır ve bu veritabanları Spring Data JPA ile yönetilmektedir. Aşağıda her bir mikroservise ait temel tablolar bulunmaktadır:

- **Order Service**: Sipariş bilgilerini tutan `orders` tablosu.
- **Inventory Service**: Ürün stok bilgilerini içeren `inventory` tablosu.
- **Payment Service**: Ödeme bilgilerini tutan `payments` tablosu.

## Kurulum ve Çalıştırma

### Gereksinimler
- Java 17+
- Docker
- Maven
- PostgreSQL
- RabbitMQ

### Adımlar

1. Depoyu klonlayın:
   ```bash
   git clone https://github.com/mcay51/sagapattern.git
    ```

2- Docker kullanarak her bir mikroservisi başlatın. Projede her mikroservis için Dockerfile dosyaları mevcuttur. Örneğin, order-service servisini çalıştırmak için:
```bash
cd order-service
docker build -t order-service .
docker run -d -p 8081:8081 order-service
 ```
3 - postgresql veritabanını Docker üzerinden başlatın:
```bash
docker run --name <CONTAINER_ADI> -e POSTGRES_PASSWORD=<ROOT_PAROLASI> -d -p 5432:5432 -v <HOST'TA_HERHANGI_BIR_DIZIN>:/var/lib/postgresql/data  postgres
 ```
4 - Mikroservisleri başlattıktan sonra, RabbitMQ mesajlaşma sistemini kurun ve her bir servisin RabbitMQ'ya bağlandığından emin olun.

5 - Uygulamayı çalıştırdıktan sonra, servislere aşağıdaki URL'ler üzerinden erişebilirsiniz:

 Order Service: http://localhost:8081/orders
 
## Lisans

Bu proje [MIT Lisansı](LICENSE) altında lisanslanmıştır.

## İletişim

Proje hakkında daha fazla bilgi almak veya katkıda bulunmak için iletişime geçebilirsiniz.

