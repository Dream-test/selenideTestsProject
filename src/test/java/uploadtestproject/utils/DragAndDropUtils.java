package uploadtestproject.utils;

import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DragAndDropUtils {

    public static void dragAndDropFile(File file, SelenideElement dragTarget) {
        SelenideElement fileInput = $("input[type='file']");
        fileInput.uploadFile(file);

        dispatchFileDragAndDropEvent("dragenter", "document", fileInput);
        dispatchFileDragAndDropEvent("dragover", "document", fileInput);
        dispatchFileDragAndDropEvent("drop", dragTarget, fileInput);
    }

    private static void dispatchFileDragAndDropEvent(String eventName, Object to, SelenideElement fileInputId) {
        String script = "var files = arguments[0].files;" +
                "var items = [];" +
                "var types = [];" +
                "for (var i = 0; i < files.length; i++) {" +
                " items[i] = {kind: 'file', type: files[i].type};" +
                " types[i] = 'Files';" +
                "}" +
                "var event = document.createEvent('CustomEvent');" +
                "event.initCustomEvent(arguments[1], true, true, 0);" +
                "event.dataTransfer = {" +
                " files: files," +
                " items: items," +
                " types: types" +
                "};" +
                "arguments[2].dispatchEvent(event);";

        if (to instanceof String) {
            script = script.replace("argument[2}", to.toString());
        } else {
            executeJavaScript(script, fileInputId, eventName, to);
        }
    }

    public static void dragAndDropFileByBaseObject(File file, SelenideElement dragTarget) throws IOException {
        // Читаем содержимое файла и кодируем в Base64
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String base64Content = Base64.getEncoder().encodeToString(fileContent);

        // JavaScript, который:
        // 1. Декодирует Base64 в бинарные данные
        // 2. Создаёт объект File с указанными данными, именем и типом
        // 3. Создаёт DataTransfer и добавляет туда файл
        // 4. Создаёт событие 'drop' с dataTransfer и диспатчит его на dragTarget
        String jsDropFile =
                "var target = arguments[0];" +
                        "var base64 = arguments[1];" +
                        "var fileName = arguments[2];" +
                        "var fileType = arguments[3];" +
                        "var binary = atob(base64);" +
                        "var array = [];" +
                        "for(var i = 0; i < binary.length; i++) {" +
                        "   array.push(binary.charCodeAt(i));" +
                        "}" +
                        "var file = new File([new Uint8Array(array)], fileName, {type: fileType});" +
                        "var dataTransfer = new DataTransfer();" +
                        "dataTransfer.items.add(file);" +
                        "var event = new DragEvent('drop', {dataTransfer: dataTransfer, bubbles: true, cancelable: true});" +
                        "target.dispatchEvent(event);";

        // Выполняем JavaScript, передавая dragTarget, base64-строку, имя файла и MIME-тип
        executeJavaScript(jsDropFile, dragTarget, base64Content, file.getName(), "image/png");
    }
}
