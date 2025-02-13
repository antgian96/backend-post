@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;

    public AuthorController(AuthorRepository authorRepository, EmailService emailService, CloudinaryService cloudinaryService) {
        this.authorRepository = authorRepository;
        this.emailService = emailService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping
    public Author createAuthor(@RequestBody @Valid Author author) {
        author.setAvatar("https://api.adorable.io/avatars/285/" + author.getEmail() + ".png");
        Author savedAuthor = authorRepository.save(author);
        emailService.sendConfirmationEmail(author.getEmail());
        return savedAuthor;
    }

    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autore non trovato"));

        String imageUrl = cloudinaryService.uploadFile(file);
        author.setAvatar(imageUrl);
        authorRepository.save(author);

        return ResponseEntity.ok("Avatar caricato con successo: " + imageUrl);
    }
}

