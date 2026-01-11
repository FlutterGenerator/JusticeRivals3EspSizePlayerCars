DWORD getAbsoluteAddress(DWORD relativeAddr, const char *libraryName = "") {
    if (libBase == 0)
        libBase = findLibrary(libraryName);
    if (libBase != 0)
        return (reinterpret_cast<DWORD>(libBase + relativeAddr));
    else
        return 0;
}

monoString *CreateMonoString(const char *str) {
monoString *(*String_CreateString)(void *instance, const char *str) = (monoString *(*)(void *, const char *))getAbsoluteAddress(0x1179674, "libil2cpp.so"); //private unsafe string CreateString(sbyte* value)

    return String_CreateString(NULL, str);
}
