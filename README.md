# clj-file-traversal

## Development

Test

```shell
lein test
```

Build documentation

```shell
lein codox
# Or
lein doc
```

Build

```shell
lein uberjar
```

Deploy to Clojars repository

```shell
lein deploy clojars
```

## TODO

1. Codox to generate also documentation for private functions.
2. Codox: document map content once, for many functions and linking to that (like @see in JavaDocs)
3. Directory handler function.
4. Pass map with callback functions, filtering options.
5. Creation time getting and adding to handling map.
