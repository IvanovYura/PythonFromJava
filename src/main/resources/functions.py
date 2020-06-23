def deduplicate(input, output):
    # set to track lines already processed
    s = set()

    with open(input) as fr, open(output, 'w+') as fw:
        for line in fr:
            if line not in s:
                s.add(line)
                fw.write(line)
