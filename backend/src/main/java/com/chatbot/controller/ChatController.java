import jdk.jfr.Registered;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/add")
    public ResponseEntity<?> addChat(@RequestBody Chat chat) {
        chatService.addChat(chat);
        return ResponseEntity.ok("Chat added successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getChats() {
        return ResponseEntity.ok(chatService.getChats());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getChat(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getChat(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok("Chat deleted successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateChat(@RequestBody Chat chat) {
        chatService.updateChat(chat);
        return ResponseEntity.ok("Chat updated successfully");
    }
}
