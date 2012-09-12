service TinyQService {
  void put(1: string queue, 2: binary data),
  binary get(1: string queue)
}