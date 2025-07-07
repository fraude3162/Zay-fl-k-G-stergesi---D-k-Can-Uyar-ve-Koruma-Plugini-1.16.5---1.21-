# ZayiflikGostergesi Minecraft Plugin

## Özellikler
- Oyuncunun canı %30'un altına düştüğünde uyarı verir
- Yanma, düşme ve yaratık saldırılarında özel mesajlar
- PVP taktik mesajları
- Resistance ve Slowness efektleri
- Oyuncular /zayıflıkgostergesi aç veya /zayıflıkgostergesi kapat komutlarıyla göstergelerini açıp kapatabilir
- 1.16.5 ile 1.21 arası tüm sürümlerde ve Java 8, 16, 17 ile uyumlu

## Kurulum
1. Projeyi IntelliJ IDEA ile açın
2. `lib` klasörüne Spigot API 1.16.5 jar dosyasını ekleyin
3. File → Project Structure → Libraries → "+" → Java → lib/spigot-api-1.16.5-R0.1-SNAPSHOT.jar dosyasını seçin
4. Build → Build Project
5. Build → Build Artifacts → ZayiflikGostergesi:jar → Build
6. Oluşan jar dosyasını sunucunuzun plugins klasörüne atın

## Komutlar
- `/zayıflıkgostergesi aç` : Göstergeni açar
- `/zayıflıkgostergesi kapat` : Göstergeni kapatır

## Notlar
- Plugin.yml ve ana Java dosyası aşağıda gösterildiği gibi olmalıdır. 