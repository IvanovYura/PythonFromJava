def deduplicate(input, output):
    # set to track lines already processed
    s = set()

    try:
        with open(input) as fr, open(output, 'w+') as fw:
            for line in fr:
                if line not in s:
                    s.add(line)
                    fw.write(line)

        # if everything good,
        # input file is closed and not other errors -> then:
        return 0

    except Exception as e:
        raise Exception('Cannot do that: ' + str(e))
