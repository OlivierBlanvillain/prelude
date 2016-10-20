# Prelude [![Travis](https://travis-ci.org/OlivierBlanvillain/prelude.svg?branch=master)](https://travis-ci.org/OlivierBlanvillain/prelude) [![Maven](https://img.shields.io/maven-central/v/in.nvilla/prelude_sjs0.6_2.11.svg?label=maven)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22in.nvilla%22%20AND%20a%3A%22prelude_2.11%22)

Alternative to `scala._` and `scala.Predef._` default import.

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

0. Explicitly import what you use from `scala._` and `scala.Predef._`:

    ```sh
    git ls-files | grep -e '.scala$' |\
    while read file; do
      for import in                               \
          "import scala.collection.immutable.Seq" \
          "import scala.collection.immutable.Set" \
          "import scala.collection.immutable.Map" \
          ; do
        class=$(echo $import | rev | cut -d "." -f1 | rev)

        if grep -q "$class" "$file" && ! grep -q "$import" "$file"; then
          sed -i.class '1,/^$/ {/^$/a'"$import"'
    }' "$file"
        fi
      done
    done
    ```
