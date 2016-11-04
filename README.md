# Prelude [![Travis](https://travis-ci.org/OlivierBlanvillain/prelude.svg?branch=master)](https://travis-ci.org/OlivierBlanvillain/prelude) [![Maven](https://img.shields.io/maven-central/v/in.nvilla/prelude_2.12.svg?label=maven)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22in.nvilla%22%20AND%20a%3A%22prelude_2.12%22)

Alternative to `import java.lang._, scala._, scala.Predef._`. Prelude is a *subset* of the default imports, meaning that any code compiled under `-Yno-imports` & `import prelude._` should also compile with the default scalac configuration.

#### build.sbt:

```scala
scalacOptions in ThisBuild +=
  "-Yno-imports"

libraryDependencies +=
  "in.nvilla" %%% "prelude" % "latest.integration"
```

#### \*.scala:

```scala
import prelude._
```

#### Suggested migration:

0. Update `build.sbt`

0. Add a prelude import after the first empty line of each `.scala` file:

    ```sh
    git ls-files | grep -e '.scala$' |\
    while read file; do
      if ! grep -q "import prelude._" "$file"; then
        sed -i.class '1,/^$/ {/^$/aimport prelude._
    }' "$file"
      fi
    done
    ```

0. Explicitly import what your project uses `scala._` and `scala.Predef._`:

    ```sh
    git ls-files | grep -e '.scala$' |\
    while read file; do
      for import in                               \
          "import scala.collection.immutable.Seq" \
          "import scala.collection.immutable.Set" \
          "import scala.collection.immutable.Map" \
          ; do # ^ Add imports as needed
        class=$(echo $import | rev | cut -d "." -f1 | rev)

        if grep -q "$class" "$file" && ! grep -q "$import" "$file"; then
          sed -i.class '1,/^$/ {/^$/a'"$import"'
    }' "$file"
        fi
      done
    done
    ```
