package io.github.underscore11code.carboncord.api.misc;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BiRegistryGetter<K, A, B> {
  @Nullable K ofA(@NonNull A a);

  @Nullable K ofB(@NonNull B b);

  @Nullable A a(@NonNull K k);

  @Nullable B b(@NonNull K k);

  @Nullable A toA(@NonNull B b);

  @Nullable B toB(@NonNull A a);

}
